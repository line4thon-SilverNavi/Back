package org.likelion._thon.silver_navi.domain.user.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.user.entity.enums.RelationRole;

@Getter
public class SignUpReq {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^[0-9]{9,11}$",
            message = "전화번호는 숫자만 포함하고 9~11자리여야 합니다."
    )
    private String phone;

    @Schema(description = "부모/배우자 | !전부 소문자로 적기!")
    @NotNull(message = "관계는 필수값입니다.")
    private RelationRole relation;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{6,20}$",
            message = "영문, 숫자, 특수문자를 포함한 6~20자리 이내로 입력해주세요.")
    private String password;

    private String passwordCheck;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        if (password == null || passwordCheck == null) return false;
        return password.equals(passwordCheck);
    }
}
