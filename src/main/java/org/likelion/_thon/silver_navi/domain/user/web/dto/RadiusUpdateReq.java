package org.likelion._thon.silver_navi.domain.user.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RadiusUpdateReq {
    @NotNull(message = "거리 반경은 필수 입력 항목입니다.")
    @Min(value = 3, message = "검색 반경은 3~7km 이어야 합니다.")
    @Max(value = 7, message = "검색 반경은 3~7km 이어야 합니다.")
    private Integer searchRadius;
}
