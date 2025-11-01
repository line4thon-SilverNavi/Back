package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;

import java.math.BigDecimal;
import java.util.List;

public record UserByFacilityInfoRes (
        @JsonUnwrapped NursingFacilityDetailInfoRes info,
        double distanceKm,
        BigDecimal averageRating,
        long reviewCount,
        List<ReviewRes> reviews
) {
    public static UserByFacilityInfoRes from(NursingFacility facility, double userLat, double userLng) {
        double distance = GeoUtils.calculateDistance(userLat, userLng, facility.getLatitude(), facility.getLongitude());
        double distanceKm = Math.round(distance * 10.0) / 10.0;
        return new UserByFacilityInfoRes(
                NursingFacilityDetailInfoRes.from(facility),
                distanceKm,
                facility.getAverageRating(),
                facility.getReviewCount(),
                facility.getReviews().stream().map(ReviewRes::from).toList()
        );
    }
}
