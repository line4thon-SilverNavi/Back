package org.likelion._thon.silver_navi.domain.review.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class ReviewCreateReq {
    @NotNull(message = "별점을 입력해주세요.")
    @Range(min = 0, max = 5, message = "별점은 정수 0~5 값으로 입력해주세요.")
    Integer rating;

    @NotBlank(message = "후기 내용을 입력해주세요.")
    @Size(max = 150, message = "후기 내용은 150자 이내로 입력해주세요.")
    String content;
}
