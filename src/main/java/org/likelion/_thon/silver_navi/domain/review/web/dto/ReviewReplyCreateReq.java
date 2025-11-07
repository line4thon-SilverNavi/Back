package org.likelion._thon.silver_navi.domain.review.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewReplyCreateReq {

    @NotBlank(message = "리뷰 답변 내용을 입력해주세요.")
    private String content;
}
