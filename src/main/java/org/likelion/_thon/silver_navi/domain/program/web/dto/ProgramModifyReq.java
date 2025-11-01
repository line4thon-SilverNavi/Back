package org.likelion._thon.silver_navi.domain.program.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProgramModifyReq {

    @Schema(description = "null이면 유지")
    private String name;                // 프로그램명 - null이면 유지

    @Schema(
            description = "프로그램 카테고리, null이면 유지",
            allowableValues = {"건강", "문화", "치료"}
    )
    private String category;            // 카테고리 - null이면 유지

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String instructorName;      // 강사명 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지")
    @Future(message = "프로그램 날짜는 미래의 날짜여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;             // 일정 - null이면 유지

    @Schema(description = "프로그램 시작 시간 (HH:mm 형식), null이면 유지", example = "13:30")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;        // 시작 시간 - null이면 유지

    @Schema(description = "프로그램 시작 시간 (HH:mm 형식), null이면 유지", example = "17:30")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;          // 종료 시간 - null이면 유지

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String location;            // 장소 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, -1이면 제거")
    private Integer capacity;           // 정원 - null이면 유지, -1이면 제거

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String fee;                // 참가비 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    @Pattern(
            regexp = "^(|\\d{2,4}-\\d{3,4}-\\d{4})$",
            message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678 또는 02-123-4567)"
    )
    private String number;              // 문의 전화 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, 빈 문자열(\"\")이면 제거")
    private String description;         // 프로그램 설명 - null이면 유지, 빈 문자열("")이면 제거

    @Schema(description = "null이면 유지, 빈 리스트([])면 제거")
    private List<String> supplies;      // 준비물 - null이면 유지, 빈 리스트([])면 제거

    // --- 파일 수정을 위한 필드 ---

    @Schema(description = "새로 교체할 프로그램 기획서 파일 (보내지 않으면 기존 파일 유지)")
    private MultipartFile proposal;     // 첨부파일 (교체)

    @Schema(description = "기존 기획서를 삭제할지 여부 (true로 보내면 기존 파일 삭제)")
    private Boolean isDeleteProposal = false;     // 기획서 삭제 플래그

    @Schema(description = "'새로 추가할' 이미지 파일 리스트")
    private List<MultipartFile> newImages; // 새로 추가할 프로그램 사진들

    @Schema(description = "삭제하지 않고 '유지할' 기존 이미지 URL 목록")
    private List<String> existingImageUrls;  // 삭제할 기존 프로그램 사진 URL들
}
