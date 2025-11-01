package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import org.likelion._thon.silver_navi.domain.review.entity.Review;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewRes(
        Long id,
        String authorName,
        BigDecimal rating,
        String content,
        LocalDateTime createdAt
) {
    public static ReviewRes from(Review review) {
        return new ReviewRes(
                review.getId(),
                review.getUser().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
