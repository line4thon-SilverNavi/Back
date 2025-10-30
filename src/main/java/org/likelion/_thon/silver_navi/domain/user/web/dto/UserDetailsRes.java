package org.likelion._thon.silver_navi.domain.user.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.caretarget.entity.CareTarget;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;
import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.Gender;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

import java.time.LocalDate;

public record UserDetailsRes(
        @JsonUnwrapped UserInfoRes info,
        String careNumber          // 요양인정번호
) {
    public static UserDetailsRes from(User user) {
        CareTarget careTarget = user.getCareTarget();

        return new UserDetailsRes(
                UserInfoRes.from(user),
                careTarget != null ? careTarget.getCareNumber() : null
        );
    }
}