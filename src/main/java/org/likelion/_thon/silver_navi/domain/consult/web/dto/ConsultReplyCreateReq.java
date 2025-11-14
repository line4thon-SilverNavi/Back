package org.likelion._thon.silver_navi.domain.consult.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;

import java.time.LocalDate;

@Getter
public class ConsultReplyCreateReq {

    @NotNull(message = "상담 ID는 필수 입력 항목입니다.")
    private Long consultId;             // PK

    @NotNull(message = "상담 종류는 필수 입력 항목입니다.")
    private ConsultCategory category;   // 일반상담, 시설상담

    @NotBlank(message = "답변 내용은 필수 입력 항목입니다.")
    private String content;             // 상담 답변 내용

    @NotNull(message = "상담 확정 날짜는 필수 입력 항목입니다.")
    private LocalDate confirmedDate;        // 상담 확정 날짜

    @NotNull(message = "상담 확정 시간대는 필수 입력 항목입니다.")
    private ConsultTime confirmedTime;      // 상담 확정 시간대, enum: MORNING / AFTERNOON / EVENING
}
