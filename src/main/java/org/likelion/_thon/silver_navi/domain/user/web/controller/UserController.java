package org.likelion._thon.silver_navi.domain.user.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.user.service.UserService;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    // 회원 가입
    @Override
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signup(@RequestBody @Valid SignUpReq signupReq) {
        userService.signUp(signupReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}
