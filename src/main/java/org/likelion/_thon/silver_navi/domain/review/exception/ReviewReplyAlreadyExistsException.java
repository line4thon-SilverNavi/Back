package org.likelion._thon.silver_navi.domain.review.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ReviewReplyAlreadyExistsException extends BaseException {
    public ReviewReplyAlreadyExistsException() {
        super(ReviewErrorCode.REVIEW_REPLY_ALREADY_EXISTS);
    }
}
