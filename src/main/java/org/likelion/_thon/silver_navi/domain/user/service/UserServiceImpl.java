package org.likelion._thon.silver_navi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.exception.UserAlreadyExistException;
import org.likelion._thon.silver_navi.domain.user.repository.UserRepository;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
