package org.likelion._thon.silver_navi.domain.review.service;

import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewInfoRes;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewPageRes;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewReplyCreateReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void createReview(User user, Long facilityId, ReviewCreateReq req);

    // 리뷰 요약 정보 및 리뷰 전체 조회
    ReviewPageRes getReviews(ManagerPrincipal managerPrincipal, Integer rating, Pageable pageable);

    // 리뷰 단일 조회
    ReviewInfoRes getReview(ManagerPrincipal managerPrincipal, Long reviewId);

    // 리뷰 삭제
    void deleteReview(ManagerPrincipal managerPrincipal, Long reviewId);

    void createReviewReply(
            ManagerPrincipal managerPrincipal, Long reviewId, ReviewReplyCreateReq reviewReplyCreateReq);
}
