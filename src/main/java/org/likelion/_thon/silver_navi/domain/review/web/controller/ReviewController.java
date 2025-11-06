package org.likelion._thon.silver_navi.domain.review.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.web.dto.BookmarkToggleRes;
import org.likelion._thon.silver_navi.domain.review.service.ReviewService;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{facilityId}")
    public ResponseEntity<SuccessResponse<?>> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long facilityId,
            @RequestBody @Valid ReviewCreateReq req
    ) {
        reviewService.createReview(userDetails.getUser(), facilityId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}
