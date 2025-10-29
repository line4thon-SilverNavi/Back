package org.likelion._thon.silver_navi.domain.manager.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ManagerSignUpReq {

    @NotBlank(message = "시설명을 입력해주세요.")
    private String name;

    @NotBlank(message = "제휴코드를 입력해주세요.")
    private String affiliateCode;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{6,20}$",
            message = "영문, 숫자, 특수문자를 포함한 6~20자리 이내로 입력해주세요."
    )
    private String password;

    private String passwordCheck; // 비밀번호 확인

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        if (password == null || passwordCheck == null) return false;
        return password.equals(passwordCheck);
    }
}
