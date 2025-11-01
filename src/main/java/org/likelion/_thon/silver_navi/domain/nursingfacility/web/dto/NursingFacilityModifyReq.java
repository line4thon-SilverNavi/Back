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

    @Schema(description = "null이면 유지")
    private String name;                    // 프로그램명 - null이면 유지

    @Schema(
            description = "시설 카테고리, null이면 유지",
            allowableValues = {"요양병원", "요양원/요양센터", "데이케어센터"}
    )
    private String category;                // 카테고리 - null이면 유지

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String operatingHours;          // 운영 시간 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    @Pattern(
            regexp = "^(|\\d{2,3}-\\d{3,4}-\\d{4})$",
            message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678 또는 02-123-4567)."
    )
    private String number;                  // 문의 전화 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지")
    private String address;                 // 시설 주소 - null이면 유지

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String description;             // 시설 설명 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, 빈 리스트([])면 제거")
    private List<String> mainServices;      // 메인서비스 - null이면 유지, 빈 리스트([])면 제거

    @Schema(description = "'새로 추가할' 이미지 파일 리스트")
    private List<MultipartFile> newImages;  // 새로 추가할 프로그램 사진들

    @Schema(description = "삭제하지 않고 '유지할' 기존 이미지 URL 목록")
    private List<String> existingImageUrls; // 삭제할 기존 프로그램 사진 URL들
}
