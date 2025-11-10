package org.likelion._thon.silver_navi.domain.user.web.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class UserUpdateReq {
    private String careTargetName;      // 돌봄대상자 이름
    private RelationRole relationRole;  // 보호자와의 관계 (enum)
    @Pattern(
            regexp = "^[0-9]{9,11}$",
            message = "전화번호는 숫자만 포함하고 9~11자리여야 합니다."
    )
    private String careTargetPhone;     // 돌봄대상자 핸드폰번호
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate careTargetBirth;  // 돌봄대상자 생년월일
    private CareGrade careGrade;        // 요양등급 (enum)
    private Gender careTargetGender;    // 돌봄대상자 성별 (enum)
    private String guardianName;        // 보호자 이름
}
