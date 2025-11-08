package org.likelion._thon.silver_navi.domain.consult.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.service.ConsultService;
import org.likelion._thon.silver_navi.domain.consult.web.dto.*;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consults")
@RequiredArgsConstructor
public class ConsultController implements ConsultApi {
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

    // 상담 내역 반환
    @GetMapping
    public ResponseEntity<SuccessResponse<ConsultHistorySummaryRes>> getConsultHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        ConsultHistorySummaryRes res = consultService.getConsultHistory(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

    // ---------------------------------------- 시설관리자 ----------------------------------------

    @Override
    @GetMapping("/management")
    public ResponseEntity<SuccessResponse<ConsultManagementRes>> getConsultManagement(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @RequestParam(name = "status", required = false) String statusStr,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ConsultStatus status = ConsultStatus.fromValue(statusStr);

        ConsultManagementRes consultManagementRes = consultService.getConsultManagement(
                managerPrincipal, status, pageable
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(consultManagementRes));
    }

    @Override
    @GetMapping("/{consultId}/{category}")
    public ResponseEntity<SuccessResponse<ConsultDetailInfoRes>> getConsult(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long consultId,
            @PathVariable String category
    ) {
        ConsultCategory consultCategory = ConsultCategory.fromValue(category);

        ConsultDetailInfoRes consultDetailInfoRes = consultService.getConsult(
                managerPrincipal, consultId, consultCategory
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(consultDetailInfoRes));
    }

    @PatchMapping("/{consultId}/{category}")
    public ResponseEntity<SuccessResponse<?>> updateConsult(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long consultId,
            @PathVariable String category,
            @RequestBody @Valid ConsultConfirmReq consultConfirmReq
    ) {
        ConsultCategory consultCategory = ConsultCategory.fromValue(category);

        consultService.updateConsult(
                managerPrincipal, consultId, consultCategory, consultConfirmReq
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.emptyCustom("변경이 성공적으로 되었습니다."));
    }
}
