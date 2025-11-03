package org.likelion._thon.silver_navi.domain.program.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.program.web.dto.ApplicationManagementRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

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

}
