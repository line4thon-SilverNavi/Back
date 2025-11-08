package org.likelion._thon.silver_navi.domain.consult.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultConfirmReq;
import org.likelion._thon.silver_navi.domain.consult.web.dto.ConsultDetailInfoRes;
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


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상담 상세 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "일반상담",
                                            value = """
                                                    {
                                                         "isSuccess": true,
                                                         "timestamp": "2025-11-09 00:46:55",
                                                         "code": "GLOBAL_200",
                                                         "httpStatus": 200,
                                                         "message": "호출에 성공하였습니다.",
                                                         "data": {
                                                             "consultCategory": "일반상담",
                                                             "status": "대기중",
                                                             "appliedAt": "2025-11-08T17:17:22.870649",
                                                             "name": "홍길동",
                                                             "phone": "01012345678",
                                                             "birthDate": "1965-04-15",
                                                             "age": 60,
                                                             "grade": "4등급",
                                                             "careName": null,
                                                             "carePhone": null,
                                                             "hopeDate": null,
                                                             "hopeTime": null,
                                                             "consultType": null,
                                                             "inquiryType": "시설 이용 문의",
                                                             "inquiryContent": "요양 서비스 이용 절차가 궁금합니다."
                                                         }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "시설상담",
                                            value = """
                                                    {
                                                        "isSuccess": true,
                                                        "timestamp": "2025-11-09 00:47:31",
                                                        "code": "GLOBAL_200",
                                                        "httpStatus": 200,
                                                        "message": "호출에 성공하였습니다.",
                                                        "data": {
                                                            "consultCategory": "시설상담",
                                                            "status": "대기중",
                                                            "appliedAt": "2025-11-08T17:18:56.489802",
                                                            "name": "김민지",
                                                            "phone": "01098765432",
                                                            "birthDate": "1965-04-15",
                                                            "age": 60,
                                                            "grade": "4등급",
                                                            "careName": null,
                                                            "carePhone": null,
                                                            "hopeDate": "2025-11-15",
                                                            "hopeTime": "오후",
                                                            "consultType": "대면",
                                                            "inquiryType": null,
                                                            "inquiryContent": "비대면 상담으로 진행하고 싶습니다."
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "상담 카테고리값 유효 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-09 00:48:43",
                                                "code": "CONSULT_400_2",
                                                "httpStatus": 400,
                                                "message": "유효하지 않은 카테고리 값입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상담 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": false,
                                                 "timestamp": "2025-11-09 00:48:19",
                                                 "code": "CONSULT_404_1",
                                                 "httpStatus": 404,
                                                 "message": "해당 상담을 찾을 수 없습니다.",
                                                 "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ConsultDetailInfoRes>> getConsult(
            ManagerPrincipal managerPrincipal, Long consultId, String category
    );


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "상담 확정 일/시간대 또는 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-09 03:27:07",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "변경이 성공적으로 되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 값",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-09 03:31:55",
                                                "code": "GLOBAL_400_2",
                                                "httpStatus": 400,
                                                "message": "입력된 값이 유효하지 않습니다. Enum 타입의 철자나 대소문자를 다시 확인해주세요.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> updateConsult(
            ManagerPrincipal managerPrincipal, Long consultId, String category, ConsultConfirmReq consultConfirmReq
    );
}
