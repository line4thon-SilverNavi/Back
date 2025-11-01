package org.likelion._thon.silver_navi.domain.program.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.program.entity.enums.ProgramCategory;
import org.likelion._thon.silver_navi.domain.program.service.ProgramService;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramDetailInfoRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramSummaryInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController implements ProgramApi {

    private final ProgramService programService;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> createProgram(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @ParameterObject @ModelAttribute @Valid ProgramCreateReq programCreateReq
    ) {
        programService.programCreate(managerPrincipal, programCreateReq);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.created(null));
    }

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<ProgramListRes>> getPrograms(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @RequestParam(name = "category", required = false) String categoryStr,
            @PageableDefault(size = 6, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        ProgramCategory category = ProgramCategory.fromValue(categoryStr);

        ProgramListRes programListRes = programService.getPrograms(managerPrincipal, category, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(programListRes));
    }

    @GetMapping("/{programId}")
    public ResponseEntity<SuccessResponse<ProgramDetailInfoRes>> getProgram(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long programId
    ) {
        ProgramDetailInfoRes programDetailInfoRes = programService.getProgram(managerPrincipal, programId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.from(programDetailInfoRes));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<SuccessResponse<?>> deleteProgram(
            @AuthenticationPrincipal ManagerPrincipal managerPrincipal,
            @PathVariable Long programId
    ) {
        programService.deleteProgram(managerPrincipal, programId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.emptyCustom("프로그램이 성공적으로 삭제되었습니다."));
    }
}
