package org.likelion._thon.silver_navi.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityNotFoundException;
import org.likelion._thon.silver_navi.domain.nursingfacility.repository.NursingFacilityRepository;
import org.likelion._thon.silver_navi.domain.review.entity.Review;
import org.likelion._thon.silver_navi.domain.review.exception.ReviewAlreadyExistsException;
import org.likelion._thon.silver_navi.domain.review.repository.ReviewRepository;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final NursingFacilityRepository nursingFacilityRepository;

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
    }
}
