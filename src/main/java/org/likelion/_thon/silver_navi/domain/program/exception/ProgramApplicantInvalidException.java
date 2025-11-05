package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ProgramApplicantInvalidException extends BaseException {
    public ProgramApplicantInvalidException() {
        super(ProgramErrorCode.PROGRAM_APPLICANT_INVALID);
    }
}
