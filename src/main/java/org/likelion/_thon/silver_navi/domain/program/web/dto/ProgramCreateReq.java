package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProgramCreateReq {

    @NotBlank(message = "프로그램명을 입력해주세요.")
    private String name;                // 프로그램명

    @Schema(
            description = "프로그램 카테고리",
            allowableValues = {"건강", "문화", "치료"}
    )
    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;            // 카테고리

    private String instructorName;      // 강사명

    @NotNull(message = "날짜를 입력해주세요.")
    @FutureOrPresent(message = "프로그램 날짜는 오늘 또는 미래의 날짜여야 합니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;             // 일정

    @Schema(description = "프로그램 시작 시간 (HH:mm 형식)", example = "13:30")
    @NotNull(message = "시작 시간을 입력해주세요.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;        // 시작 시간

    @Schema(description = "프로그램 시작 시간 (HH:mm 형식)", example = "17:30")
    @NotNull(message = "종료 시간을 입력해주세요.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;          // 종료 시간

    private String location;            // 장소

    @Min(value = 1, message = "정원은 1명 이상이어야 합니다.")
    private Integer capacity;           // 정원

    private String fee;                // 참가비

    @Pattern(
            regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678 또는 02-123-4567)."
    )
    private String number;              // 문의 전화

    private String description;         // 프로그램 설명

    private List<String> supplies;      // 준비물

    @Schema(description = "프로그램 기획서 파일")
    private MultipartFile proposal;     // 첨부파일

    @Schema(description = "이미지 파일 리스트")
    @Size(max = 5, message = "프로그램 사진은 최대 5장까지 업로드할 수 있습니다.")
    private List<MultipartFile> images; // 프로그램 사진들
}
