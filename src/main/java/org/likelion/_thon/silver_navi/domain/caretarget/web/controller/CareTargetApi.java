package org.likelion._thon.silver_navi.domain.caretarget.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.caretarget.web.dto.OcrRes;
import org.likelion._thon.silver_navi.global.response.ErrorResponse;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "돌봄대상자 API", description = "돌봄대상자 관련 API")
public interface CareTargetApi {
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "OCR 분석 성공 및 대상 정보 반환",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "isSuccess": true,
                                  "timestamp": "2025-10-29 09:18:44",
                                  "code": "GLOBAL_201",
                                  "httpStatus": 201,
                                  "message": "호출에 성공하였습니다.",
                                  "data": {
                                    "careNumber": "2024-123456",
                                    "careGrade": "2등급"
                                  }
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "OCR 서비스 외부 API 통신 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "isSuccess": false,
                                  "timestamp": "2025-10-29 09:18:44",
                                  "code": "OCR_502_1",
                                  "httpStatus": 502,
                                  "message": "Clova OCR API 요청 중 오류가 발생했습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            )
    })public ResponseEntity<SuccessResponse<OcrRes>> analyzeOcr(@RequestParam MultipartFile image)throws IOException;
}
