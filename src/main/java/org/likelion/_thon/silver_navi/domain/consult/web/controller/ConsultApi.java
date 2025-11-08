package org.likelion._thon.silver_navi.domain.consult.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultManagementRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "상담 API", description = "상담 관련 API")
public interface ConsultApi {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상담 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-11-08 17:54:59",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "호출에 성공하였습니다.",
                                                 "data": {
                                                     "summary": {
                                                         "totalCount": 2,
                                                         "pendingCount": 2,
                                                         "approvedCount": 0,
                                                         "completedCount": 0
                                                     },
                                                     "consults": [
                                                         {
                                                             "consultId": 1,
                                                             "consultDate": "2025-11-08",
                                                             "consultCategory": "상담",
                                                             "name": "김민지",
                                                             "relationRole": "자녀",
                                                             "phone": "01098765432",
                                                             "status": "대기중"
                                                         },
                                                         {
                                                             "consultId": 1,
                                                             "consultDate": "2025-11-08",
                                                             "consultCategory": "일반상담",
                                                             "name": "홍길동",
                                                             "relationRole": null,
                                                             "phone": "01012345678",
                                                             "status": "대기중"
                                                         }
                                                     ],
                                                     "pageInfo": {
                                                         "totalPages": 1,
                                                         "totalElements": 2,
                                                         "currentPage": 1,
                                                         "pageSize": 5
                                                     }
                                                 }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 상태 값",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-08 17:56:02",
                                                "code": "CONSULT_400_1",
                                                "httpStatus": 400,
                                                "message": "유효하지 않은 상태 값입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ConsultManagementRes>> getConsultManagement(
            ManagerPrincipal managerPrincipal, String statusStr, Pageable pageable
    );
}
