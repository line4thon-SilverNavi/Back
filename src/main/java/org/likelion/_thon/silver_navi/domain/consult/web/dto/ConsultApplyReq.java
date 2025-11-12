package org.likelion._thon.silver_navi.domain.consult.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultType;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

@Getter
public class ConsultApplyReq {
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

    @NotNull(message = "관계는 필수 입력 항목입니다.")
    private RelationRole relationRole;

    @NotNull(message = "희망 상담 날짜는 필수 입력 항목입니다.")
    private LocalDate hopeDate;

    @NotNull(message = "희망 상담 시간대는 필수 입력 항목입니다.")
    private ConsultTime hopeTime;       // enum: MORNING / AFTERNOON / EVENING

    @NotNull(message = "상담 종류는 필수 입력 항목입니다.")
    private ConsultType consultType;    // enum: FACE_TO_FACE / NON_FACE_TO_FACE

    private String content;
}
