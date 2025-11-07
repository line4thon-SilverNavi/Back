package org.likelion._thon.silver_navi.domain.review.service;

import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewPageRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void createReview(User user, Long facilityId, ReviewCreateReq req);

    // 리뷰 요약 정보 및 리뷰 전제 조회
    ReviewPageRes getReviews(ManagerPrincipal managerPrincipal, Integer rating, Pageable pageable);
}
