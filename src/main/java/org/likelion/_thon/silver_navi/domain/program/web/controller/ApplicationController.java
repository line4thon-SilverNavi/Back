package org.likelion._thon.silver_navi.domain.program.web.controller;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ApplicationStatus;
import org.likelion._thon.silver_navi.domain.program.service.ApplicationService;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
