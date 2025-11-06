package org.likelion._thon.silver_navi.domain.review.service;

import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewCreateReq;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface ReviewService {
    void createReview(User user, Long facilityId, ReviewCreateReq req);
}
