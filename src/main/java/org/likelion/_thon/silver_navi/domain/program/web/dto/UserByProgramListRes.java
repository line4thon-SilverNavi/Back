package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.program.entity.Program;

public record UserByProgramListRes (
        @JsonUnwrapped ProgramSummaryInfoRes info,
        String thumbnail,
        boolean bookmarked
){
    public static UserByProgramListRes from(Program program, String thumbnail ,boolean bookmarked) {
        return new UserByProgramListRes(
                ProgramSummaryInfoRes.from(program),
                thumbnail,
                bookmarked
        );
    }
}

