package org.likelion._thon.silver_navi.domain.manager.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.manager.web.dto.CheckLoginIdReq;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignInRes;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSignUpReq;
import org.likelion._thon.silver_navi.domain.manager.web.dto.ManagerSingInReq;
import org.likelion._thon.silver_navi.global.response.ErrorResponse;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "시설 관리자 API", description = "시설 관리자 관련 API")
public interface ManagerApi {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "아이디 사용 가능",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-10-29 11:01:39",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "사용 가능한 아이디입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 아이디",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-29 11:03:27",
                                                "code": "MANAGER_409_1",
                                                "httpStatus": 409,
                                                "message": "이미 존재하는 아이디입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<SuccessResponse<?>> check(CheckLoginIdReq checkLoginIdReq);

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

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-11-11 12:27:24",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "name": "성신노인요양원",
                                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE2Nzc2NTU2OTZ9.dummy-signature-string-for-example"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "비밀번호 일치 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-29 11:07:50",
                                                "code": "MANAGER_401_1",
                                                "httpStatus": 401,
                                                "message": "비밀번호가 일치하지 않습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 아이디",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-29 11:04:41",
                                                "code": "MANAGER_404_1",
                                                "httpStatus": 404,
                                                "message": "존재하지 않는 시설 관리자입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ManagerSignInRes>> signin(ManagerSingInReq managerSingInReq);
}
