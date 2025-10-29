package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import java.util.List;

public record NursingFacilityDetailInfoRes(
        String name,
        String category,
        String operatingHours,
        String number,
        String address,
        String description,
        List<String> mainServices,
        List<String> images
) {
}
