package org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class FacilityAccessDeniedException extends BaseException {
    public FacilityAccessDeniedException() {
        super(NursingFacilityErrorCode.FACILITY_ACCESS_DENIED);
    }
}
