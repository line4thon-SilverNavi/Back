package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;
import java.time.Period;

public record ApplicationUserInfoRes(
        String programName,         // 프로그램명
        String applicationDate,     // 신청 날짜

        String applicantName,       // 신청자 이름
        String applicantPhone,      // 신청자 번호

        String careName,            // 보호자 이름
        String carePhone,           // 보호자 번호

        Integer age,                // 신청자 나이
        String careGrade,           // 신청자 요양 등급
        String content,             // 특이사항
        String status               // 신청 상태
) {
    public static ApplicationUserInfoRes from(ProgramApply programApply) {
        Program program = programApply.getProgram();
        User user = programApply.getUser();
        CareTarget careTarget = user.getCareTarget();

        String applicantName = null;
        String applicantPhone = null;
        String careName = null;
        String carePhone = null;
        if (user.getRelation().equals(RelationRole.SELF)) {
            applicantName = user.getName();
            applicantPhone = user.getPhone();
        } else {
            applicantName = careTarget.getName();
            applicantPhone = careTarget.getCareNumber();
            careName = user.getName();
            carePhone = user.getPhone();
        }

        Integer age = Period.between(careTarget.getBirthDate(), LocalDate.now()).getYears();

        return new ApplicationUserInfoRes(
                program.getName(),
                programApply.getCreatedAt().toString(),
                applicantName,
                applicantPhone,
                careName,
                carePhone,
                age,
                careTarget.getCareGrade().getLabel(),
                programApply.getContent(),
                programApply.getStatus().getValue()
        );
    }
}
