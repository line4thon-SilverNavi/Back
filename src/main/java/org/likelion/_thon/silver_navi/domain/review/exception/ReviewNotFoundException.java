package org.likelion._thon.silver_navi.domain.review.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ReviewNotFoundException extends BaseException {
    public ReviewNotFoundException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}
