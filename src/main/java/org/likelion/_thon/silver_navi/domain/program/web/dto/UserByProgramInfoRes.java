package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.program.entity.Program;

public record UserByProgramInfoRes(
        @JsonUnwrapped ProgramDetailInfoRes info,
        boolean bookmarked                              // 유저의 좋아요 여부
) {
    public static UserByProgramInfoRes from(Program program, boolean bookmarked) {
        return new UserByProgramInfoRes(
                ProgramDetailInfoRes.from(program),
                bookmarked
        );
    }
}
