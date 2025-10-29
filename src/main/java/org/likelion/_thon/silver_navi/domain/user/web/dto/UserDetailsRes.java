package org.likelion._thon.silver_navi.domain.user.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

public record UserDetailsRes (
        String userName,             // 사용자 이름
        String userPhone,            // 사용자 전화번호
        String careTargetName,       // 돌봄대상자 이름
        LocalDate careTargetBirth,   // 돌봄대상자 생년월일
        Gender careTargetGender,     // 돌봄대상자 성별 (enum)
        RelationRole relationRole,   // 보호자와의 관계 (enum)
        String address,              // 돌봄대상자 주소
        String careNumber,           // 요양인정번호
        CareGrade careGrade          // 요양등급 (enum)){
){
    public static UserDetailsRes from(User user) {
        CareTarget careTarget = user.getCareTarget();

        return new UserDetailsRes(
                user.getName(),
                user.getPhone(),
                careTarget != null ? careTarget.getName() : null,
                careTarget != null ? careTarget.getBirthDate() : null,
                careTarget != null ? careTarget.getGender() : null,
                user.getRelation(),
                careTarget != null ? careTarget.getAddress() : null,
                careTarget != null ? careTarget.getCareNumber() : null,
                careTarget != null ? careTarget.getCareGrade() : null
        );
    }
}
