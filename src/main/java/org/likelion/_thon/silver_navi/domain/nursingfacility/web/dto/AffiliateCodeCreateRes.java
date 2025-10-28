package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import java.time.LocalDateTime;

public record AffiliateCodeCreateRes(
        Long facilityId,
        String affiliateCode,
        LocalDateTime createdAt
) {
}
