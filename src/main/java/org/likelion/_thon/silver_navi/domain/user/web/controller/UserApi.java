package org.likelion._thon.silver_navi.domain.user.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.likelion._thon.silver_navi.global.response.ErrorResponse;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자 API", description = "사용자 관련 API")
public interface UserApi {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "사용자 회원가입 성공",
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
                    responseCode = "409",
                    description = "아이디(전화번호) 중복",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "isSuccess": false,
                                      "timestamp": "2025-10-27 10:10:21",
                                      "code": "SIGNUP_409_1",
                                      "httpStatus": 409,
                                      "message": "이미 존재하는 아이디입니다.",
                                      "data": null
                                    }
                                    """
                            )
                    )
            )
    })
    public ResponseEntity<SuccessResponse<?>> signup(@RequestBody @Valid SignUpReq signupReq);
}
