package org.likelion._thon.silver_navi.domain.user.service;

import org.likelion._thon.silver_navi.domain.caretarget.repository.CareTargetRepository;
import org.likelion._thon.silver_navi.domain.user.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.exception.InvalidCredentialsException;
import org.likelion._thon.silver_navi.domain.user.exception.UserAlreadyExistException;
import org.likelion._thon.silver_navi.domain.user.repository.UserRepository;
import org.likelion._thon.silver_navi.domain.user.web.dto.*;
import org.likelion._thon.silver_navi.global.auth.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CareTargetRepository careTargetRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Override
    @Transactional
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

    //유저 정보 수정
    @Override
    @Transactional
    public void updateUser(User user, UserUpdateReq dto) {
        User targetUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException());

        targetUser.updatePartial(dto);

        CareTarget careTarget = careTargetRepository.findByUser(targetUser).orElse(null);

        if (careTarget == null) {
            CareTarget newCareTarget = CareTarget.create(dto, targetUser);
            targetUser.setCareTarget(newCareTarget);
        } else {
            careTarget.updatePartial(dto);
        }

    }

    //프로그램 신청 시 유저 정보 반환
    @Override
    public UserInfoRes userInfo(User user) {
        UserInfoRes res = UserInfoRes.from(user);
        return res;
    }
}
