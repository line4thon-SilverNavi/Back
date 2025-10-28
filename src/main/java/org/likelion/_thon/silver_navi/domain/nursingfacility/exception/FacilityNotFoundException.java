package org.likelion._thon.silver_navi.domain.nursingfacility.exception;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class FacilityNotFoundException extends BaseException {
    public FacilityNotFoundException() { super(NursingFacilityErrorCode.FACILITY_NOT_FOUND); }
}
