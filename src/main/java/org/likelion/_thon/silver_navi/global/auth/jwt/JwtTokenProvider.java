package org.likelion._thon.silver_navi.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.likelion._thon.silver_navi.domain.manager.exception.ManagerNotFoundException;
import org.likelion._thon.silver_navi.domain.manager.repository.ManagerRepository;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.exception.UserNotFoundException;
import org.likelion._thon.silver_navi.domain.user.repository.UserRepository;
import org.likelion._thon.silver_navi.global.auth.UserRole;
import org.likelion._thon.silver_navi.global.auth.security.CustomManagerDetails;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {this.key = Keys.hmacShaKeyFor(secretKey.getBytes()); }

    public String createToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getPhone())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }
    public String createToken(Manager manager) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(String.valueOf(manager.getId()))
                .claim("role", manager.getRole())
                .claim("facilityId", manager.getNursingFacility().getId())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검사 + 만료 검사
    public boolean validateToken(String token) {
        try{
            getClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    // Claim 파싱
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = claims.get("role", String.class);

        if (role.equals(UserRole.USER.name())) {
            String phone = claims.getSubject();

            User user = userRepository.findByPhone(phone)
                    .orElseThrow(UserNotFoundException::new);

            CustomUserDetails userDetails = new CustomUserDetails(user);

            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        } else if (role.equals(UserRole.ADMIN.name())) {
            Long id = Long.parseLong(claims.getSubject());
            Long facilityId = claims.get("facilityId", Long.class);

            ManagerPrincipal managerPrincipal = new ManagerPrincipal(id, role, facilityId);

            return new UsernamePasswordAuthenticationToken(
                    managerPrincipal,
                    null,
                    managerPrincipal.getAuthorities()
            );
        } else {
            return null;
        }
    }
}
