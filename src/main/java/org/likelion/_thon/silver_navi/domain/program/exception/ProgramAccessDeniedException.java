package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ProgramAccessDeniedException extends BaseException {
    public ProgramAccessDeniedException() {
        super(ProgramErrorCode.PROGRAM_ACCESS_DENIED);
    }
}
