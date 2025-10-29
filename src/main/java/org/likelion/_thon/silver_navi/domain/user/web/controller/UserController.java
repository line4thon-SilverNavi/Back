package org.likelion._thon.silver_navi.domain.user.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.service.UserService;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInRes;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.UserDetailsRes;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    //로그인
    @Override
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<SignInRes>> signin(@RequestBody @Valid SignInReq signInReq){
        SignInRes signInRes = userService.signIn(signInReq);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(signInRes));
    }

    //유저 상세 정보 반환
    @GetMapping("/details")
    public ResponseEntity<SuccessResponse<UserDetailsRes>> userDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserDetailsRes detailsRes = userService.userDetails(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(detailsRes));
    }
}
