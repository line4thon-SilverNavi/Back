package org.likelion._thon.silver_navi.domain.program.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class ProgramCategoryInvalidException extends BaseException {
    public ProgramCategoryInvalidException() { super(ProgramErrorCode.PROGRAM_CATEGORY_INVALID); }
}
