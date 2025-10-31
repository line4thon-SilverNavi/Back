package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums.FacilityCategory;

import java.math.BigDecimal;

public record NearbyFacilityRes(
        Long facilityId,
        String name,
        String thumbnail,
        double distanceKm,
        BigDecimal averageRating,
        long reviewCount,
        String operatingHours,
        String phoneNumber,
        FacilityCategory category
) {
    public static NearbyFacilityRes of(NursingFacility facility, double distanceKm,
                                       BigDecimal avgRating, long reviewCount) {
        return new NearbyFacilityRes(
                facility.getId(),
                facility.getName(),
                facility.getImageUris().isEmpty() ? null : facility.getImageUris().get(0),
                Math.round(distanceKm * 10) / 10.0,
                avgRating,
                reviewCount,
                facility.getOperatingHours(),
                facility.getFacilityNumber(),
                facility.getCategory()
        );
    }
}
