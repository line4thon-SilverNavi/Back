package org.likelion._thon.silver_navi.domain.nursingfacility.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.nursingfacility.exception.nursingfacility.FacilityCategoryInvalidException;

@RequiredArgsConstructor
public enum FacilityCategory {

    NURSING_HOSPITAL("요양병원"),
    NURSING_HOME("요양원/요양센터"),
    DAYCARE_CENTER("데이케어센터");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    public static FacilityCategory fromValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        for (FacilityCategory facilityCategory : values()) {
            if (facilityCategory.getValue().equals(value)) {
                return facilityCategory;
            }
        }
        throw new FacilityCategoryInvalidException();
    }
}
