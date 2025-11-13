package org.likelion._thon.silver_navi.domain.program.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationStatusUpdateReq;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationUserInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "신청 관리 API", description = "프로그램 신청 관련 API")
public interface ApplicationApi {

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
                                                "timestamp": "2025-11-03 01:57:06",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "summary": {
                                                        "totalCount": 1,
                                                        "pendingCount": 1,
                                                        "approvedCount": 0,
                                                        "rejectedCount": 0
                                                    },
                                                    "applications": [
                                                        {
                                                            "applicationId": 1,
                                                            "applicationDate": "2025-11-03",
                                                            "programName": "활기찬 아침 체조",
                                                            "applicantName": "정종진",
                                                            "phone": "01012345678",
                                                            "status": "대기중"
                                                        }
                                                    ],
                                                    "pageInfo": {
                                                        "totalPages": 1,
                                                        "totalElements": 1,
                                                        "currentPage": 1,
                                                        "pageSize": 5
                                                    }
                                                }
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ApplicationManagementRes>> getApplications(
            ManagerPrincipal managerPrincipal, String statusStr, Pageable pageable
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신청자 상세 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "관계가 \"본인\"일 때",
                                            value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-11-03 16:26:13",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "호출에 성공하였습니다.",
                                                 "data": {
                                                     "programName": "활기찬 아침 체조",
                                                     "applicationDate": "2025-11-03T16:22:23.475421",
                                                     "applicantName": "이본인",
                                                     "applicantPhone": "01012345678",
                                                     "careName": null,
                                                     "carePhone": null,
                                                     "age": 75,
                                                     "careGrade": "3등급",
                                                     "content": "a",
                                                     "status": "대기중"
                                                 }
                                            }
                                            """
                                    ),
                                    @ExampleObject(
                                            name = "관계가 \"본인이 아닐\" 때",
                                            value = """
                                                    {
                                                        "isSuccess": true,
                                                        "timestamp": "2025-11-03 16:33:53",
                                                        "code": "GLOBAL_200",
                                                        "httpStatus": 200,
                                                        "message": "호출에 성공하였습니다.",
                                                        "data": {
                                                            "programName": "활기찬 아침 체조",
                                                            "applicationDate": "2025-11-03T16:32:17.985844",
                                                            "applicantName": "이영희",
                                                            "applicantPhone": "3024-123555",
                                                            "careName": "누구누구",
                                                            "carePhone": "01056781234",
                                                            "age": 75,
                                                            "careGrade": "3등급",
                                                            "content": "a",
                                                            "status": "대기중"
                                                        }
                                                    }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "신청 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": false,
                                                 "timestamp": "2025-11-03 16:26:50",
                                                 "code": "APPLICATION_404_1",
                                                 "httpStatus": 404,
                                                 "message": "해당 신청을 찾을 수 없습니다.",
                                                 "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ApplicationUserInfoRes>> getApplicationInfo(
            ManagerPrincipal managerPrincipal, Long applicationId
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신청 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-11-03 20:17:04",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "신청 상태가 변경되었습니다.",
                                                 "data": null
                                             }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "신청 거부했지만, 거부 사유 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": false,
                                                 "timestamp": "2025-11-03 20:14:00",
                                                 "code": "APPLICATION_400_2",
                                                 "httpStatus": 400,
                                                 "message": "거부 사유를 입력해주세요.",
                                                 "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "신청 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-03 20:15:01",
                                                "code": "APPLICATION_404_1",
                                                "httpStatus": 404,
                                                "message": "해당 신청을 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 처리된 신청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-03 20:18:16",
                                                "code": "APPLICATION_409_1",
                                                "httpStatus": 409,
                                                "message": "이미 처리된(승인 또는 거절된) 신청입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> updateApplicationInfo(
            ManagerPrincipal managerPrincipal, Long applicationId, ApplicationStatusUpdateReq applicationStatusUpdateReq
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "신청 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-13 21:18:50",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": [
                                                    {
                                                        "applicationId": 1,
                                                        "applicationDate": "2025-11-13",
                                                        "programName": "활기찬 아침 체조",
                                                        "applicantName": "김보호",
                                                        "phone": "01011112222",
                                                        "status": "대기중"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<List<ApplicationManagementRes.ApplicationInfoRes>>> searchApplications(
            ManagerPrincipal managerPrincipal, String keyword
    );
}
