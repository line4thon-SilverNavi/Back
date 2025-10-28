package org.likelion._thon.silver_navi.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInReq {
    @NotBlank(message = "아이디(번호)를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
