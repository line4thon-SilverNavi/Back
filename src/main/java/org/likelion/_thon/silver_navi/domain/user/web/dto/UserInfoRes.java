package org.likelion._thon.silver_navi.domain.user.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

public record UserInfoRes(
        String guardianName,         // 보호자 이름
        String guardianPhone,        // 보호자 전화번호
        String careTargetName,       // 돌봄대상자 이름
        String careTargetPhone,      // 돌봄대상자 번호
        LocalDate careTargetBirth,   // 돌봄대상자 생년월일
        Gender careTargetGender,     // 돌봄대상자 성별 (enum)
        RelationRole relationRole,   // 보호자와의 관계 (enum)
        CareGrade careGrade          // 요양등급 (enum))
) {
    public static UserInfoRes from(User user) {
        CareTarget careTarget = user.getCareTarget();

        return new UserInfoRes(
                user.getName(),
                user.getPhone(),
                careTarget != null ? careTarget.getName() : null,
                careTarget != null ? careTarget.getPhoneNumber() : null,
                careTarget != null ? careTarget.getBirthDate() : null,
                careTarget != null ? careTarget.getGender() : null,
                user.getRelation(),
                careTarget != null ? careTarget.getCareGrade() : null
        );
    }
}
