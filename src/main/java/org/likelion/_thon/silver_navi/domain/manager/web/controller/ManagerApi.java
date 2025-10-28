package org.likelion._thon.silver_navi.domain.manager.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignUpReq;
import org.likelion._thon.silver_navi.global.response.ErrorResponse;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "시설 관리자 API", description = "시설 관리자 관련 API")
public interface ManagerApi {
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "시설 관리자 회원가입 성공",
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
            @ApiResponse(
                    responseCode = "400",
                    description = "제휴코드 일치 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "isSuccess": false,
                                      "timestamp": "2025-10-28 13:48:06",
                                      "code": "CODE_400_1",
                                      "httpStatus": 400,
                                      "message": "제휴코드가 일치하지 않습니다.",
                                      "data": null
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스 존재 X (시설 또는 제휴코드)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "FACILITY_NOT_FOUND",
                                            value = """
                                            {
                                              "isSuccess": false,
                                              "timestamp": "2025-10-28 13:33:31",
                                              "code": "FACILITY_404_1",
                                              "httpStatus": 404,
                                              "message": "해당 요양 시설을 찾을 수 없습니다.",
                                              "data": null
                                            }
                                            """
                                    ),
                                    @ExampleObject(
                                            name = "CODE_NOT_FOUND",
                                            value = """
                                            {
                                              "isSuccess": false,
                                              "timestamp": "2025-10-28 13:51:51",
                                              "code": "CODE_404_1",
                                              "httpStatus": 404,
                                              "message": "제휴코드가 존재하지 않습니다.",
                                              "data": null
                                            }
                                            """
                                    )
                            }
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<?>> signup(ManagerSignUpReq managerSignUpReq);
}
