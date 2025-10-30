package org.likelion._thon.silver_navi.domain.program.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.program.web.dto.ProgramCreateReq;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

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
}
