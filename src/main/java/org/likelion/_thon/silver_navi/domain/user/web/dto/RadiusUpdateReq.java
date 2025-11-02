package org.likelion._thon.silver_navi.domain.user.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class RadiusUpdateReq {
    @NotNull(message = "거리 반경은 필수 입력 항목입니다.")
    @Range(min = 3, max = 7, message = "검색 반경은 3~7km 이어야 합니다.")
    private Integer searchRadius;
}
