package org.likelion._thon.silver_navi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.exception.InvalidCredentialsException;
import org.likelion._thon.silver_navi.domain.user.exception.UserAlreadyExistException;
import org.likelion._thon.silver_navi.domain.user.repository.UserRepository;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInRes;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.UserDetailsRes;
import org.likelion._thon.silver_navi.global.auth.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Override
    public void signUp(SignUpReq signUpReq) {
        if(userRepository.existsByPhone(signUpReq.getPhone())){
            throw new UserAlreadyExistException();
        }
        String encoded = passwordEncoder.encode(signUpReq.getPassword());
        User user = User.toEntity(
                signUpReq.getName(), signUpReq.getPhone(), signUpReq.getRelation(),encoded);

        userRepository.save(user);
    }

    //로그인
    @Override
    public SignInRes signIn(SignInReq signInReq) {
        // 아이디 확인
        User user = userRepository.findByPhone(signInReq.getId())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(signInReq.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(user);

        // 반환
        return new SignInRes(token);
    }

    //유저 상세 정보 반환
    @Override
    public UserDetailsRes userDetails(User user) {
        UserDetailsRes res = UserDetailsRes.from(user);
        return res;
    }
}
