package org.likelion._thon.silver_navi.domain.review.web.dto;

import org.likelion._thon.silver_navi.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewInfoRes(
        Long reviewId,              // 리뷰 ID (PK)
        String authorName,          // 작성자
        String content,             // 내용
        int rating,                 // 평점
        LocalDateTime createdDate   // 작성일(시간, 분, 초까지)
) {
    public static ReviewInfoRes from(Review review) {
        return new ReviewInfoRes(
                review.getId(),
                review.getUser().getName(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt()
        );
    }
}
