package org.likelion._thon.silver_navi.domain.consult.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.service.GeneralConsultService;
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
    private final GeneralConsultService generalConsultService;

    // 일반 상담 신청
    @PostMapping("/general")
    public ResponseEntity<SuccessResponse<?>> applyGeneralConsult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid GeneralApplyReq req) {
        generalConsultService.apply(userDetails.getUser(),req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    // 상세 상담 신청
//    @PostMapping("/detail")
//    public ResponseEntity<SuccessResponse<?>> applyDetailConsult(
//            @RequestBody @Valid DetailApplyReq req) {
//        detailConsultService.apply(req);
//        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
//    }
}
