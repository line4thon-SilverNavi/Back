package org.likelion._thon.silver_navi.domain.review.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.review.service.ReviewService;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewInfoRes;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewPageRes;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {
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

    // ---------------------------------------- 시설 관리자 ----------------------------------------

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<ReviewPageRes>> getReviews(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @RequestParam(name = "rating", required = false) Integer rating,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        ReviewPageRes reviewPageRes = reviewService.getReviews(managerPrincipal, rating, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(reviewPageRes));
    }

    @Override
    @GetMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<ReviewInfoRes>> getReview(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long reviewId
    ) {

        ReviewInfoRes reviewInfoRes = reviewService.getReview(managerPrincipal, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(reviewInfoRes));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<SuccessResponse<?>> deleteReview(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(managerPrincipal, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.emptyCustom("리뷰가 성공적으로 삭제되었습니다."));
    }
}
