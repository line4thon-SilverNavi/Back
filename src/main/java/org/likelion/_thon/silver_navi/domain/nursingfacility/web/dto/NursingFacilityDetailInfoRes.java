package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import org.likelion._thon.silver_navi.domain.nursingfacility.entity.NursingFacility;

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
    public static NursingFacilityDetailInfoRes from(NursingFacility nursingFacility) {
        return new NursingFacilityDetailInfoRes(
                nursingFacility.getName(),
                nursingFacility.getCategory() != null ? nursingFacility.getCategory().getValue() : null,
                nursingFacility.getOperatingHours(),
                nursingFacility.getFacilityNumber(),
                nursingFacility.getAddress(),
                nursingFacility.getDescription(),
                nursingFacility.getServices(),
                nursingFacility.getImageUris()
        );
    }
}
