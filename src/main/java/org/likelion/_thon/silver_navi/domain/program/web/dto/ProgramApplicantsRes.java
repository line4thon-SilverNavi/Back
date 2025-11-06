package org.likelion._thon.silver_navi.domain.program.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.program.entity.ProgramApply;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;
import java.time.Period;

public record ProgramApplicantsRes(
        Long applicantId,
        String name, // 보호자가 있다면 보호대상(careTarget) 이름, 아니라면 자기(user) 이름
        String gender,
        Integer age,

        String careName, // 보호자가 있다면 보호자(user) 이름, 아니라면 null
        String phone, // user, 보호자가 있으면 보호자(user) 번호, 자기가 직접 가입해도 (user) 자기 번호

        String attendanceStatus // 출결 상태
) {
    public static ProgramApplicantsRes from(ProgramApply programApply) {
        User user = programApply.getUser();
        CareTarget careTarget = user.getCareTarget();

        String name;
        String careName = null;

        if (programApply.getUser().getRelation().equals(RelationRole.SELF)) {
            name = user.getName();
        } else {
            name = careTarget.getName();
            careName = user.getName();
        }

        Integer age = Period.between(careTarget.getBirthDate(), LocalDate.now()).getYears();

        return new ProgramApplicantsRes(
                programApply.getId(),
                name,
                careTarget.getGender().getValue(),
                age,

                careName,
                user.getPhone(),

                programApply.getAttendanceStatus().getValue()
        );
    }
}
