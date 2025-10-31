package org.likelion._thon.silver_navi.domain.nursingfacility.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityModifyReq;
import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NursingFacilityDetailInfoRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "시설 API", description = "시설 관련 API")
public interface NursingFacilityApi {

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "시설 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-10-29 15:59:43",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "facilityName": "성신노인요양원",
                                                    "category": null,
                                                    "operatingHours": null,
                                                    "facilityNumber": "0507-1335-8538",
                                                    "facilityAddress": "서울시 성북구 정릉동 123-45",
                                                    "description": null,
                                                    "mainServices": [],
                                                    "images": []
                                                }
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<NursingFacilityDetailInfoRes>> getNursingFacility(
            ManagerPrincipal managerPrincipal
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "시설 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "timestamp": "2025-10-29 17:39:28",
                                                "code": "GLOBAL_200",
                                                "httpStatus": 200,
                                                "message": "호출에 성공하였습니다.",
                                                "data": {
                                                    "name": "성신노인요양원",
                                                    "category": "요양원/요양센터",
                                                    "operatingHours": "오전 10:30 ~ 저녁 08:00",
                                                    "number": "0507-1335-8538",
                                                    "address": "서울시 성북구 정릉동 123-45",
                                                    "description": null,
                                                    "mainServices": [
                                                        "A",
                                                        "B",
                                                        "C"
                                                    ],
                                                    "images": [
                                                        "img1",
                                                        "img2"
                                                    ]
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-10-29 16:47:58",
                                                "code": "FACILITY_400_1",
                                                "httpStatus": 400,
                                                "message": "유효하지 않은 카테고리 값입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<NursingFacilityDetailInfoRes>> updateNursingFacility(
            ManagerPrincipal managerPrincipal,
            NursingFacilityModifyReq nursingFacilityDeatailsInfoReq
    );
}
