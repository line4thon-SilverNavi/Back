package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;

public interface NursingFacilityService {

    NursingFacilityDetailInfoRes getFacility(Long managerId, Long facilityId);
}
