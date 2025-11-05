package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ProgramApplicantNotFoundException extends BaseException {
    public ProgramApplicantNotFoundException() {
        super(ProgramErrorCode.PROGRAM_APPLICANT_NOT_FOUND);
    }
}
