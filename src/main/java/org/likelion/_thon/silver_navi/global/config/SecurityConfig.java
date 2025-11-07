package org.likelion._thon.silver_navi.global.config;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.global.auth.exception.CustomAccessDeniedHandler;
import org.likelion._thon.silver_navi.global.auth.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // swagger
                        .requestMatchers("/docs", "/docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 사용자
                        .requestMatchers("/api/users/signup","/api/users/signin").permitAll()
                        // 시설 관리자
                        .requestMatchers("/api/managers/check-id",
                                "/api/managers/signup", "/api/managers/signin").permitAll()
                        // 제휴코드
                        .requestMatchers("/api/code/create").permitAll()

                        // ---------- 여기는 권한이 필요한 곳  ----------
                        // --- USER ---
                        .requestMatchers(HttpMethod.GET, "/api/applications/list").hasRole("USER")

                        // --- ADMIN ---
                        // 시설
                        .requestMatchers(HttpMethod.GET, "/api/facilities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/facilities").hasRole("ADMIN")
                        // 프로그램
                        .requestMatchers(HttpMethod.POST, "/api/programs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/programs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/programs/{programId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/programs/{programId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/programs/{programId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/programs/{programId}/applications").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/programs/{programId}/applications").hasRole("ADMIN")
                        // 프로그램 신청
                        .requestMatchers(HttpMethod.GET, "/api/applications/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/applications/{applications}").hasRole("ADMIN")
                        // 리뷰
                        .requestMatchers(HttpMethod.GET, "/api/reviews").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reviews/{reviewId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/{reviewId}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(ex -> ex
                                .accessDeniedHandler(customAccessDeniedHandler)
                );
        return http.build();
    }
}
