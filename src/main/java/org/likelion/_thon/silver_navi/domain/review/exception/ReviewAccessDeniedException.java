package org.likelion._thon.silver_navi.domain.review.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ReviewAccessDeniedException extends BaseException {
    public ReviewAccessDeniedException() {
        super(ReviewErrorCode.REVIEW_ACCESS_DENIED);
    }
}
