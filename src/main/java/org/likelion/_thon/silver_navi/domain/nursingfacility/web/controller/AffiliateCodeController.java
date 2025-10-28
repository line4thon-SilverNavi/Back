package org.likelion._thon.silver_navi.domain.nursingfacility.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.service.AffiliateCodeService;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.AffiliateCodeCreateRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class AffiliateCodeController {

    private final AffiliateCodeService codeService;

    @Value("${admin.secret-key}")
    private String adminSecretKey;

    @Hidden // Swagger에서 숨김
    @PostMapping("/create")
    public ResponseEntity<AffiliateCodeCreateRes> createPartnershipCode(
            @RequestParam("facilityId") Long facilityId,
            @RequestHeader("Server-Admin-Key") String keyFromHeader
    ) {
        if (!adminSecretKey.equals(keyFromHeader)) {
            // 키가 틀리면 401 (Unauthorized) 또는 403 (Forbidden) 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AffiliateCodeCreateRes response = codeService.createPartnershipCode(facilityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
