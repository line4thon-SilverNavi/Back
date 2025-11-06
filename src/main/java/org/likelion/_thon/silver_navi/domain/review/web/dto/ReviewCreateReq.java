package org.likelion._thon.silver_navi.domain.review.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReviewCreateReq {
    @NotNull(message = "별점을 입력해주세요.")
    @DecimalMin(value = "0.0", inclusive = true, message = "별점은 0.0 이상 5.0 이하로 입력해주세요.")
    @DecimalMax(value = "5.0", inclusive = true, message = "별점은 0.0 이상 5.0 이하로 입력해주세요.")
    BigDecimal rating;

    @NotBlank(message = "후기 내용을 입력해주세요.")
    @Size(max = 150, message = "후기 내용은 150자 이내로 입력해주세요.")
    String content;
}
