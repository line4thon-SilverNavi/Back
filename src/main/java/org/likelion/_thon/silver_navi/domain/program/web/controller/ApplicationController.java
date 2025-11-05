package org.likelion._thon.silver_navi.domain.program.web.controller;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.web.dto.BookmarkSummaryRes;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.service.ApplicationService;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController implements ApplicationApi {

    private final ApplicationService applicationService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<ApplicationManagementRes>> getApplications(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @RequestParam(name = "status", required = false) String statusStr,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ApplicationStatus applicationStatus = ApplicationStatus.fromValue(statusStr);

        ApplicationManagementRes applicationManagementRes = applicationService.getApplications(
                managerPrincipal, applicationStatus, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(applicationManagementRes));
    }

    @Override
    @GetMapping("/{applicationId}")
    public ResponseEntity<SuccessResponse<ApplicationUserInfoRes>> getApplicationInfo(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long applicationId
    ) {
        ApplicationUserInfoRes applicationUserInfoRes = applicationService.getApplicationInfo(
                managerPrincipal, applicationId
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(applicationUserInfoRes));

    }

    @PatchMapping("/{applicationId}")
    public ResponseEntity<SuccessResponse<?>> updateApplicationInfo(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusUpdateReq applicationStatusUpdateReq
    ) {
        applicationService.updateApplicationStatus(managerPrincipal, applicationId, applicationStatusUpdateReq);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.emptyCustom("신청 상태가 변경되었습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse<ApplicationSummaryRes>> getUserByApplications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        ApplicationSummaryRes res = applicationService.getUserByApplications(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
