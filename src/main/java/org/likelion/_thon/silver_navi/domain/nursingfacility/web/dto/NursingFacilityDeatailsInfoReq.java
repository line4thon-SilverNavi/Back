package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NursingFacilityDeatailsInfoReq {
    private String name;

    @Schema(
            description = "시설 카테고리 (null 가능)",
            allowableValues = {"요양병원", "요양원/요양센터", "데이케어센터"}
    )
    private String category;

    private String operatingHours;
    private String number;
    private String address;
    private String description;
    private List<String> mainServices;

    @Schema(description = "삭제하지 않고 '유지할' 기존 이미지 URL 목록")
    private List<String> existingImageUrls;
    @Schema(description = "'새로 추가할' 이미지 파일 목록")
    private List<MultipartFile> newImages;
}
