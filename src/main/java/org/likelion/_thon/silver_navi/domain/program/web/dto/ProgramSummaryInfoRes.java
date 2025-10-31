package org.likelion._thon.silver_navi.domain.program.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.likelion._thon.silver_navi.domain.program.entity.Program;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public record ProgramSummaryInfoRes(
        Long programId,             // PK
        String programName,         // 프로그램명
        String location,            // 장소
        String category,            // 카테고리
        LocalDate date,             // 일정
        String dayOfWeek,        // 요일

        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,        // 시작 시간

        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,          // 종료 시간

        Integer currentApplicants,  // 신청 인원
        Integer capacity,           // 전체 정원
        String fee                  // 참가비
) {
    public static ProgramSummaryInfoRes from(Program program) {
        String location = program.getLocation() != null
                ? program.getLocation() : program.getNursingFacility().getAddress();

        String koreanDayOfWeek = program.getDate().getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        return new ProgramSummaryInfoRes(
                program.getId(),
                program.getName(),
                location,
                program.getCategory().getValue(),
                program.getDate(),
                koreanDayOfWeek,
                program.getStartTime(),
                program.getEndTime(),
                program.getCurrentParticipant(),
                program.getCapacity(),
                program.getFee()
        );
    }
}
