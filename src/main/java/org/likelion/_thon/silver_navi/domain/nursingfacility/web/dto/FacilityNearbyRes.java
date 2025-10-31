package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums.FacilityCategory;

import java.math.BigDecimal;

public record FacilityNearbyRes(
        String name,
        String thumbnail,
        double distance,
        BigDecimal averageRating,
        long reviewCount,
        String operatingHours,
        String phoneNumber,
        FacilityCategory category
) {}
