package org.likelion._thon.silver_navi.domain.program.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramListRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "프로그램 API", description = "프로그램 관련 API")
public interface ProgramApi {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "프로그램 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "isSuccess": true,
                                              "timestamp": "2025-10-27 10:10:21",
                                              "code": "GLOBAL_201",
                                              "httpStatus": 201,
                                              "message": "호출에 성공하였습니다.",
                                              "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> createProgram(
            ManagerPrincipal managerPrincipal, ProgramCreateReq programCreateReq
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로그램 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-10-31 11:44:30",
                                                "code": "GLOBAL_201",
                                                "httpStatus": 201,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "programs": [
                                                        {
                                                            "programId": 2,
                                                            "programName": "활기찬 아침 치료",
                                                            "location": "서울시 성북구 정릉동 123-45",
                                                            "category": "치료",
                                                            "date": "2025-11-28",
                                                            "dayOfWeek": "금",
                                                            "startTime": "09:30",
                                                            "endTime": "10:30",
                                                            "currentApplicants": 0,
                                                            "capacity": 30,
                                                            "fee": "무료"
                                                        },
                                                        {
                                                            "programId": 1,
                                                            "programName": "활기찬 아침 체조",
                                                            "location": "3층 대강당",
                                                            "category": "건강",
                                                            "date": "2025-11-28",
                                                            "dayOfWeek": "금",
                                                            "startTime": "09:30",
                                                            "endTime": "10:30",
                                                            "currentApplicants": 0,
                                                            "capacity": 30,
                                                            "fee": "무료"
                                                        }
                                                    ],
                                                    "pageInfo": {
                                                        "totalPages": 1,
                                                        "totalElements": 2,
                                                        "currentPage": 1,
                                                        "pageSize": 6
                                                    }
                                                }
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ProgramListRes>> getPrograms(
            ManagerPrincipal managerPrincipal, String categoryStr, Pageable pageable
    );
}