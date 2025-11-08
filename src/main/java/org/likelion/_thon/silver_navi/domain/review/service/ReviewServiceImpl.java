package org.likelion._thon.silver_navi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.repository.NotificationRepository;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.review.entity.ReviewReply;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewAccessDeniedException;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewAlreadyExistsException;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewNotFoundException;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewReplyAlreadyExistsException;
import org.likelion._thon.silver_navi.domain.review.repository.ReviewRepository;
import org.likelion._thon.silver_navi.domain.review.web.dto.*;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewPageRes.PageInfo;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final NursingFacilityRepository nursingFacilityRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createReview(User user, Long facilityId,  ReviewCreateReq req) {
        NursingFacility facility = nursingFacilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);

        if (reviewRepository.existsByUserAndNursingFacility(user, facility)) {
            throw new ReviewAlreadyExistsException();
        }

        Review review = Review.create(user, facility, req);
        reviewRepository.save(review);

        updateFacilityReviewStats(facility, req.getRating());
    }

    private void updateFacilityReviewStats(NursingFacility facility, Integer newRate) {
        long oldCount = facility.getReviewCount() == null ? 0L : facility.getReviewCount();
        BigDecimal oldAvg = facility.getAverageRating() == null ? BigDecimal.ZERO : facility.getAverageRating();

        long newCount = oldCount + 1;

        BigDecimal newTotal = oldAvg.multiply(BigDecimal.valueOf(oldCount))
                .add(BigDecimal.valueOf(newRate));

        BigDecimal newAvg = newTotal
                .divide(BigDecimal.valueOf(newCount), 2, RoundingMode.HALF_UP);

        facility.updateReviewStats(newCount, newAvg);
        nursingFacilityRepository.save(facility);
    }

    @Override
    @Transactional
    public ReviewPageRes getReviews(ManagerPrincipal managerPrincipal, Integer rating, Pageable pageable) {
        NursingFacility nursingFacility = nursingFacilityRepository.findById(managerPrincipal.getFacilityId())
                .orElseThrow(FacilityNotFoundException::new);

        List<Review> reviews = reviewRepository.findByNursingFacility(nursingFacility);
        ReviewSummaryRes summary = ReviewSummaryRes.from(reviews);

        Page<Review> reviewPage = reviewRepository.findReviews(nursingFacility, rating, pageable);
        Page<ReviewInfoRes> reviewInfoPage = reviewPage.map(ReviewInfoRes::from);

        PageInfo pageInfo = PageInfo.from(reviewInfoPage);

        return new ReviewPageRes(
                summary,
                reviewInfoPage.getContent(),
                pageInfo
        );
    }

    @Override
    public ReviewInfoRes getReview(ManagerPrincipal managerPrincipal, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ReviewAccessDeniedException();
        }

        return ReviewInfoRes.from(review);
    }

    @Override
    @Transactional
    public void deleteReview(ManagerPrincipal managerPrincipal, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ReviewAccessDeniedException();
        }

        reviewRepository.delete(review);
    }

    @Override
    @Transactional
    public void createReviewReply(
            ManagerPrincipal managerPrincipal, Long reviewId, ReviewReplyCreateReq reviewReplyCreateReq
    ) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.getNursingFacility().getId().equals(managerPrincipal.getFacilityId())) {
            throw new ReviewAccessDeniedException();
        }

        if (review.getReply() != null) {
            throw new ReviewReplyAlreadyExistsException();
        }

        ReviewReply newReply = ReviewReply.create(reviewReplyCreateReq.getContent());

        review.setReply(newReply);

        // 알림 생성 (리뷰 작성자에게)
        Notification notification = Notification.createReviewReply(
                review.getUser(),  // 알림 받을 사용자 (리뷰 작성자)
                review.getId()
        );

        notificationRepository.save(notification);
    }
}
