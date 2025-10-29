package org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility;

import org.likelion._thon.silver_navi.global.exception.BaseException;

public class FacilityCategoryInvalidException extends BaseException {
    public FacilityCategoryInvalidException() {
        super(NursingFacilityErrorCode.FACILITY_CATEGORY_INVALID);
    }
}
