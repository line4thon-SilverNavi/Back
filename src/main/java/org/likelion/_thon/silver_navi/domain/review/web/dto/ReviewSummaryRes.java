package org.likelion._thon.silver_navi.domain.review.web.dto;

import org.likelion._thon.silver_navi.domain.review.entity.Review;

import java.util.List;

public record ReviewSummaryRes(
        int totalReviews,       // 총 리뷰 수
        double averageRating,   // 평균 별점
        int fiveStarCount,      // 5점 리뷰
        int lowStarCount        // 1-2점 리뷰
) {
    public static ReviewSummaryRes from(List<Review> reviews) {

        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        int fiveStarCount = (int) reviews.stream()
                .filter(review -> review.getRating() == 5)
                .count();

        int lowStarCount = (int) reviews.stream()
                .filter(review -> review.getRating() == 1 || review.getRating() == 2)
                .count();

        return new ReviewSummaryRes(
                reviews.size(),
                averageRating,
                fiveStarCount,
                lowStarCount
        );
    }
}
