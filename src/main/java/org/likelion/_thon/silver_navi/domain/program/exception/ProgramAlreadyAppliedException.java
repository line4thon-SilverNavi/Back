package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;
import org.likelion._thon.silver_navi.global.response.code.BaseResponseCode;

public class ProgramAlreadyAppliedException extends BaseException {
    public ProgramAlreadyAppliedException() {
        super(ProgramErrorCode.PROGRAM_ALREADY_APPLIED);
    }
}
