package org.likelion._thon.silver_navi.domain.consult.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.consult.entity.Consult;
import org.likelion._thon.silver_navi.domain.consult.entity.GeneralConsult;
import org.likelion._thon.silver_navi.domain.consult.entity.enums.*;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public record ConsultDetailInfoRes(
        // 상단 공통 정보
        ConsultCategory consultCategory,    // 상담 유형 (상담, 일반상담)
        ConsultStatus status,               // 상담 상태 (대기중, 확인됨, 완료, 거부)
        LocalDateTime appliedAt,            // 신청 일시 (2025. 10. 15. 오전 10:30:00)

        // 신청자 정보
        String name,                        // 신청자 이름
        String phone,                       // 신청자 연락처
        LocalDate birthDate,                // 생년월일
        int age,                            // 신청자 나이
        CareGrade grade,                    // 요양등급 (1등급, 2등급, 3등급, 4등급, 5등급)
        String careName,                    // 보호자 이름 (없다면 null)
        String carePhone,                   // 보호자 연락처 (없다면 null)

        // 상담 정보
        LocalDate hopeDate,                 // 희망 상담 일시 (2025-10-20)
        ConsultTime hopeTime,               // 희망 상담 시간 (오전, 오후, 저녁)
        ConsultType consultType,            // 상담 유형 (대면, 비대면)
        InquiryType inquiryType,            // 문의유형 (일반 문의, 시설 이용 문의, 비용 문의, 방문 예약)
        String inquiryContent               // 문의 내용
) {
    public static ConsultDetailInfoRes from(Consult consult) {
        User user = consult.getUser();
        CareTarget careTarget = user.getCareTarget();

        String careName = null;
        String carePhone = null;
        if (!user.getRelation().equals(RelationRole.SELF)) {
            careName = user.getName();
            carePhone = user.getPhone();
        }

        // 생년월일로 현재 나이 계산
        int age = Period.between(careTarget.getBirthDate(), LocalDate.now()).getYears();

        return new ConsultDetailInfoRes(
                ConsultCategory.GRADE,
                consult.getConsultStatus(),
                consult.getCreatedAt(),
                consult.getName(),
                consult.getPhone(),
                careTarget.getBirthDate(),
                age,
                careTarget.getCareGrade(),
                careName,
                carePhone,
                consult.getHopeDate(),
                consult.getHopeTime(),
                consult.getConsultType(),
                null,
                consult.getContent()
        );
    }

    public static ConsultDetailInfoRes from(GeneralConsult consult) {
        User user = consult.getUser();
        CareTarget careTarget = user.getCareTarget();


        // 생년월일로 현재 나이 계산
        int age = Period.between(careTarget.getBirthDate(), LocalDate.now()).getYears();

        return new ConsultDetailInfoRes(
                ConsultCategory.GENERAL,
                consult.getConsultStatus(),
                consult.getCreatedAt(),
                consult.getName(),
                consult.getPhone(),
                careTarget.getBirthDate(),
                age,
                careTarget.getCareGrade(),
                user.getName(),
                user.getPhone(),
                null,
                null,
                null,
                consult.getInquiryType(),
                consult.getContent()
        );
    }
}
