package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ProgramNotFoundException extends BaseException {
    public ProgramNotFoundException() {
        super(ProgramErrorCode.PROGRAM_NOT_FOUND);
    }
}
