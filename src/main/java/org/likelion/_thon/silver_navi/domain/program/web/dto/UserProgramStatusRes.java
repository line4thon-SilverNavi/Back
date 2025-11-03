package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.program.entity.Program;
import org.likelion._thon.silver_navi.domain.program.entity.enums.UserByProgramStatus;

public record UserProgramStatusRes(
        @JsonUnwrapped UserByProgramListRes program,
        UserByProgramStatus status
) {
    public static UserProgramStatusRes of(UserByProgramListRes res, Program program) {
        return new UserProgramStatusRes(
                res,
                UserByProgramStatus.from(program.getDate())
        );
    }
}
