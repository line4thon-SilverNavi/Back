package org.likelion._thon.silver_navi.domain.nursingfacility.service;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.AffiliateCodeCreateRes;

public interface AffiliateCodeService {

    public AffiliateCodeCreateRes createPartnershipCode(Long facilityId);
}
