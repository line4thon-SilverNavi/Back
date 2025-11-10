package org.likelion._thon.silver_navi.domain.user.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.service.UserService;
import org.likelion._thon.silver_navi.domain.user.web.dto.*;
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
    public ResponseEntity<SuccessResponse<UserInfoRes>> userDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserInfoRes detailsRes = userService.userDetails(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(detailsRes));
    }

    //유저 정보 수정
    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> updateUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UserUpdateReq userUpdateReq
    ){
        userService.updateUser(userDetails.getUser(),userUpdateReq);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(null));
    }

    //프로그램 신청 시 유저 정보 반환
    @GetMapping
    public ResponseEntity<SuccessResponse<UserInfoRes>> userInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        UserInfoRes res = userService.userInfo(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

    //사용자 탐색 거리 반경 수정
    @PatchMapping("/radius")
    public ResponseEntity<SuccessResponse<?>> updateRadius(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid RadiusUpdateReq req
    ){
        userService.updateRadius(userDetails.getUser(),req);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(null));
    }

    //사용자 위치 정보 수정
    @PatchMapping("/location")
    public ResponseEntity<SuccessResponse<?>> updateLocation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid LocationUpdateReq req
    ){
        userService.updateLocation(userDetails.getUser(), req);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(null));
    }

    //사용자 마이페이지 정보 반환
    @GetMapping("/mypage")
    public ResponseEntity<SuccessResponse<UserMypageInfoRes>> userMypageInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        UserMypageInfoRes res = userService.userMypageInfo(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
