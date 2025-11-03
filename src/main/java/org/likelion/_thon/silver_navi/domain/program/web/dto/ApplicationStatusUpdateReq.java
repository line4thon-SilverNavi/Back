package org.likelion._thon.silver_navi.domain.program.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApplicationStatusUpdateReq {

    @NotNull(message = "승인 여부를 입력해주세요.")
    private Boolean isApproved;

    private String reason;
}
