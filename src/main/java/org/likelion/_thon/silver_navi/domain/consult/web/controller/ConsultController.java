package org.likelion._thon.silver_navi.domain.consult.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.service.ConsultService;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultApplyReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.GeneralApplyReq;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultController {
    private final ConsultService consultService;

    // 일반 상담 신청
    @PostMapping("/general")
    public ResponseEntity<SuccessResponse<?>> applyGeneralConsult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid GeneralApplyReq req) {
        consultService.applyGeneral(userDetails.getUser(),req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    // 상담 신청
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> applyConsult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid ConsultApplyReq req) {
        consultService.apply(userDetails.getUser(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}
