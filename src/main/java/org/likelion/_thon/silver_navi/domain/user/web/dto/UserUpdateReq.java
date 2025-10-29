package org.likelion._thon.silver_navi.domain.user.web.dto;

import lombok.Getter;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

@Getter
public class UserUpdateReq {
    private String userName;            // 사용자 이름
    private String careTargetName;      // 돌봄대상자 이름
    private LocalDate careTargetBirth;  // 돌봄대상자 생년월일
    private Gender careTargetGender;    // 돌봄대상자 성별 (enum)
    private RelationRole relationRole;  // 보호자와의 관계 (enum)
    private String address;             // 돌봄대상자 주소
    private String careNumber;          // 요양인정번호
    private CareGrade careGrade;        // 요양등급 (enum)
}
