package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.likelion._thon.silver_navi.domain.program.entity.Program;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ProgramDetailInfoRes(
        Long programId,             // PK
        String name,                // 프로그램명
        String category,            // 카테고리
        String instructorName,      // 강사명
        LocalDate date,             // 일정

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,        // 시작 시간

        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,          // 종료 시간

        String location,            // 장소
        Integer capacity,           // 정원
        String fee,                 // 참가비
        String number,              // 문의 전화
        String description,         // 프로그램 설명
        List<String> supplies,      // 준비물
        String proposal,            // 기획서 파일 URL
        List<String> images         // 사진 URL
) {
        public static ProgramDetailInfoRes from(Program program) {
                return new ProgramDetailInfoRes(
                        program.getId(),
                        program.getName(),
                        program.getCategory().getValue(),
                        program.getInstructorName(),
                        program.getDate(),
                        program.getStartTime(),
                        program.getEndTime(),
                        program.getLocation() != null ? program.getLocation() : program.getNursingFacility().getAddress(),
                        program.getCapacity(),
                        program.getFee(),
                        program.getContactPhone(),
                        program.getDescription(),
                        program.getSupplies(),
                        program.getProposalUrl(),
                        program.getImageUrls()
                );
        }
}
