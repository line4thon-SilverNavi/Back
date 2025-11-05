package org.likelion._thon.silver_navi.domain.user.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class LocationUpdateReq {
    @NotNull(message = "위도(latitude)는 필수 입력 항목입니다.")
    @Range(min = -90, max = 90, message = "위도(latitude)는 -90 ~ 90 범위로 입력해주세요.")
    private Double latitude;

    @NotNull(message = "경도(longitude)는 필수 입력 항목입니다.")
    @Range(min = -180, max = 180, message = "경도(longitude)는 -180 ~ 180 범위로 입력해주세요.")
    private Double longitude;
}
