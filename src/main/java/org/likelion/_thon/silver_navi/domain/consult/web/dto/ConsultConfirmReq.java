package org.likelion._thon.silver_navi.domain.consult.web.dto;

import lombok.Getter;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultStatus;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultTime;

import java.time.LocalDate;

@Getter
public class ConsultConfirmReq {

    private LocalDate confirmedDate;        // 상담 확정 날짜
    private ConsultTime confirmedTime;      // 상담 확정 시간대, enum: MORNING / AFTERNOON / EVENING
    private ConsultStatus consultStatus;    // 상담 상태
}
