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
    public static NearbyFacilityRes of(NursingFacility facility, double distanceKm) {
        return new NearbyFacilityRes(
                facility.getId(),
                facility.getName(),
                facility.getImageUrls().isEmpty() ? null : facility.getImageUrls().get(0),
                Math.round(distanceKm * 10) / 10.0,
                facility.getAverageRating(),
                facility.getReviewCount(),
                facility.getOperatingHours(),
                facility.getFacilityNumber(),
                facility.getCategory()
        );
    }
}
