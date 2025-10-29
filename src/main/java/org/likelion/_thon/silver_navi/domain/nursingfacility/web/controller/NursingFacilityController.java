package org.likelion._thon.silver_navi.domain.nursingfacility.web.controller;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.service.NursingFacilityService;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/facilities")
public class NursingFacilityController implements NursingFacilityApi {

    private final NursingFacilityService nursingFacilityService;

    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<NursingFacilityDetailInfoRes>> getNursingFacility(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal
    ) {
        NursingFacilityDetailInfoRes nfdir = nursingFacilityService.getFacility(
                managerPrincipal.getId(), managerPrincipal.getFacilityId()
        );

        return ResponseEntity.
                status(HttpStatus.OK).
                body(SuccessResponse.from(nfdir));
    }
}