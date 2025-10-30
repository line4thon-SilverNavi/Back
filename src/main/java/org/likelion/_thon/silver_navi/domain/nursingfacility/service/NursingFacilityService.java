package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityModifyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;

public interface NursingFacilityService {

    NursingFacilityDetailInfoRes getFacility(Long managerId, Long facilityId);

    NursingFacilityDetailInfoRes updateFacility(
            ManagerPrincipal managerPrincipal, NursingFacilityModifyReq req
    );
}
