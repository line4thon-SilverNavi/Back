package org.likelion._thon.silver_navi.domain.user.web.dto;

import org.likelion._thon.silver_navi.domain.caretarget.entity.enums.CareGrade;

public record SignInRes (
        String token,
        CareGrade careGrade
) {
}

