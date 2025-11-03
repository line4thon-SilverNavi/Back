package org.likelion._thon.silver_navi.domain.consult.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.ConsultCategory;

import java.time.LocalDateTime;

public record ConsultHistoryRes(
        Long id,
        String facilityName,
        String consultCategory, // "일반상담" / "상담"
        String consultType,     // "대면"/"비대면" 또는 null
        String consultStatus,   // "대기중"/"확인됨"/"완료"
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDateTime createdAt
) {
    public static ConsultHistoryRes fromGeneral(GeneralConsult consult) {
        return new ConsultHistoryRes(
                consult.getId(),
                consult.getFacility().getName(),
                ConsultCategory.GENERAL.getValue(),
                null,
                consult.getConsultStatus().getValue(),
                consult.getContent(),
                consult.getCreatedAt()
        );
    }

    public static ConsultHistoryRes from(Consult consult) {
        return new ConsultHistoryRes(
                consult.getId(),
                consult.getFacility().getName(),
                ConsultCategory.GRADE.getValue(),
                consult.getConsultType().getValue(),
                consult.getConsultStatus().getValue(),
                consult.getContent(),
                consult.getCreatedAt()
        );
    }
}

