package org.likelion._thon.silver_navi.domain.manager.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ManagerSingInReq {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
