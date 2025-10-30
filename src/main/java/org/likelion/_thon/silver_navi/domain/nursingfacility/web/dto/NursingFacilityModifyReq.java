package org.likelion._thon.silver_navi.domain.nursingfacility.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NursingFacilityModifyReq {

    @NotBlank(message = "시설명을 입력해주세요.")
    private String name;

    @Schema(
            description = "시설 카테고리",
            allowableValues = {"요양병원", "요양원/요양센터", "데이케어센터"}
    )
    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;

    private String operatingHours;

    @Pattern(
            regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678 또는 02-123-4567)."
    )
    private String number;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    private String description;

    private List<String> mainServices;

    @Schema(description = "삭제하지 않고 '유지할' 기존 이미지 URL 목록")
    private List<String> existingImageUrls;

    @Schema(description = "'새로 추가할' 이미지 파일 리스트")
    private List<MultipartFile> newImages;
}
