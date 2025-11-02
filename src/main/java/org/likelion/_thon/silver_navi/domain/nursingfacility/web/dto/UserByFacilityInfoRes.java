package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;
import org.likelion._thon.silver_navi.global.util.geo.GeoUtils;

import java.math.BigDecimal;
import java.util.List;

public record UserByFacilityInfoRes (
        @JsonUnwrapped NursingFacilityDetailInfoRes info,
        boolean bookmarked,         //좋아요 여부
        double distanceKm,          //사용자-시설 간에 거리(km)
        BigDecimal averageRating,   //평균 별점
        long reviewCount,           //리뷰 수
        List<ReviewRes> reviews     //리뷰 항목
) {
    public static UserByFacilityInfoRes from(NursingFacility facility, double userLat, double userLng, boolean bookmarked) {
        double distance = GeoUtils.calculateDistance(userLat, userLng, facility.getLatitude(), facility.getLongitude());
        double distanceKm = Math.round(distance * 10.0) / 10.0;
        return new UserByFacilityInfoRes(
                NursingFacilityDetailInfoRes.from(facility),
                bookmarked,
                distanceKm,
                facility.getAverageRating(),
                facility.getReviewCount(),
                facility.getReviews().stream().map(ReviewRes::from).toList()
        );
    }
}
