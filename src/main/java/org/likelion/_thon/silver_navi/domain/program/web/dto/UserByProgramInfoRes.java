package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.program.entity.Program;

public record UserByProgramInfoRes(
        @JsonUnwrapped ProgramDetailInfoRes info,
        boolean liked                              // 유저의 좋아요 여부
) {
    public static UserByProgramInfoRes from(Program program, boolean liked) {
        return new UserByProgramInfoRes(
                ProgramDetailInfoRes.from(program),
                liked
        );
    }
}
