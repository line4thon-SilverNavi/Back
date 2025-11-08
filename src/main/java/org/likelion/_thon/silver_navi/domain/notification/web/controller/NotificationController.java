package org.likelion._thon.silver_navi.domain.notification.web.controller;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultHistorySummaryRes;
import org.likelion._thon.silver_navi.domain.notification.service.NotificationService;
import org.likelion._thon.silver_navi.domain.notification.web.dto.CountRes;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    // 알림(안읽음) 갯수 반환
    @GetMapping
    public ResponseEntity<SuccessResponse<CountRes>> getCount(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        CountRes res = notificationService.getCount(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }

}
