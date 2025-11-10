package org.likelion._thon.silver_navi.domain.consult.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.InquiryType;

@Getter
public class GeneralApplyReq {
    @NotNull(message = "시설 ID는 필수 입력 항목입니다.")
    private Long facilityId;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "연락처는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^[0-9]{9,11}$",
            message = "전화번호는 숫자만 포함하고 9~11자리여야 합니다."
    )
    private String phone;

    @NotNull(message = "문의 유형은 필수 입력 항목입니다.")
    private InquiryType inquiryType;

    private String content;


}
