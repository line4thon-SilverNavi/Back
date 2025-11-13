package org.likelion._thon.silver_navi.domain.program.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.program.web.dto.*;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
                    description = "프로그램 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-10-31 11:44:30",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
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

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로그램 단일 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-10-31 19:16:44",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "호출에 성공하였습니다.",
                                                 "data": {
                                                     "programId": 1,
                                                     "name": "활기찬 아침 체조",
                                                     "category": "건강",
                                                     "instructorName": "김건강",
                                                     "date": "2025-11-28",
                                                     "startTime": "09:30",
                                                     "endTime": "10:30",
                                                     "location": "3층 대강당",
                                                     "capacity": 30,
                                                     "fee": "무료",
                                                     "number": "02-1234-5678",
                                                     "description": "아침을 깨우는 즐거운 체조 시간입니다.",
                                                     "supplies": [
                                                         "개인 물통"
                                                     ]
                                                 }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "소속된 시설의 프로그램 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-07 19:41:28",
                                                "code": "PROGRAM_403_1",
                                                "httpStatus": 403,
                                                "message": "소속된 시설의 프로그램만 조작할 수 있습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "프로그램 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-31 19:14:07",
                                                "code": "PROGRAM_404_1",
                                                "httpStatus": 404,
                                                "message": "해당 프로그램을 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ProgramDetailInfoRes>> getProgram(
            ManagerPrincipal managerPrincipal, Long programId
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로그램 단일 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-01 19:57:45",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "programId": 1,
                                                    "name": "활기찬 아침 체조",
                                                    "category": "건강",
                                                    "instructorName": "김건강",
                                                    "date": "2025-11-20",
                                                    "startTime": "09:30",
                                                    "endTime": "10:30",
                                                    "location": "3층 대강당",
                                                    "capacity": null,
                                                    "fee": null,
                                                    "number": "02-1234-5678",
                                                    "description": "아침을 깨우는 즐거운 체조 시간입니다.",
                                                    "supplies": [
                                                        "편한 복장",
                                                        "개인 물통"
                                                    ],
                                                    "proposal": null,
                                                    "images": []
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "소속된 시설의 프로그램 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-07 19:41:28",
                                                "code": "PROGRAM_403_1",
                                                "httpStatus": 403,
                                                "message": "소속된 시설의 프로그램만 조작할 수 있습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "프로그램 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-31 19:14:07",
                                                "code": "PROGRAM_404_1",
                                                "httpStatus": 404,
                                                "message": "해당 프로그램을 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ProgramDetailInfoRes>> modifyProgram(
            ManagerPrincipal managerPrincipal, Long programId, ProgramModifyReq programModifyReq
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로그램 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-01 13:46:06",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "프로그램이 성공적으로 삭제되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "소속된 시설의 프로그램 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-07 19:41:28",
                                                "code": "PROGRAM_403_1",
                                                "httpStatus": 403,
                                                "message": "소속된 시설의 프로그램만 조작할 수 있습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "프로그램 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-31 19:14:07",
                                                "code": "PROGRAM_404_1",
                                                "httpStatus": 404,
                                                "message": "해당 프로그램을 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> deleteProgram(
            ManagerPrincipal managerPrincipal, Long programId
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로그램 신청 요약 정보 및 신청자 리스트 조회 성공(신청 승인된 사람들만)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-05 13:09:55",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "summary": {
                                                        "totalApplicants": 1,
                                                        "attendanceCount": 0,
                                                        "attendanceRate": 0.0
                                                    },
                                                    "applicants": [
                                                        {
                                                            "applicantId": 1,
                                                            "name": "김보호",
                                                            "gender": "male",
                                                            "age": 60,
                                                            "careName": null,
                                                            "phone": "01011112222",
                                                            "attendanceStatus": "결석"
                                                        }
                                                    ]
                                                }
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ProgramApplicationInfoRes>> getProgramApplicants(
            ManagerPrincipal managerPrincipal, Long programId
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "출결 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-01 13:46:06",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "출결이 성공적으로 변경되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> updateAttendanceStatus(
            ManagerPrincipal managerPrincipal, Long programId, AttendanceUpdateReq attendanceUpdateReq
    );


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "출결 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-13 18:32:14",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": [
                                                    {
                                                        "programId": 2,
                                                        "programName": "수채화 그리기",
                                                        "location": "미술실",
                                                        "category": "문화",
                                                        "date": "2025-11-24",
                                                        "dayOfWeek": "월",
                                                        "startTime": "14:00",
                                                        "endTime": "15:30",
                                                        "currentApplicants": 0,
                                                        "capacity": 15,
                                                        "fee": "재료비 5,000원"
                                                    },
                                                    {
                                                        "programId": 5,
                                                        "programName": "추억의 영화 상영",
                                                        "location": "시청각실",
                                                        "category": "문화",
                                                        "date": "2025-11-27",
                                                        "dayOfWeek": "목",
                                                        "startTime": "14:00",
                                                        "endTime": "16:00",
                                                        "currentApplicants": 0,
                                                        "capacity": 30,
                                                        "fee": "무료"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<List<ProgramSummaryInfoRes>>> searchPrograms(
            ManagerPrincipal managerPrincipal,
            String keyword
    );

    // -------------------------------------------------- 사용자 API --------------------------------------------------
}