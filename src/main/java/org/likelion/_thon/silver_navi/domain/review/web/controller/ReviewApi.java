package org.likelion._thon.silver_navi.domain.review.web.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewInfoRes;
import org.likelion._thon.silver_navi.domain.review.web.dto.ReviewPageRes;
import org.likelion._thon.silver_navi.global.auth.jwt.ManagerPrincipal;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "리뷰 API", description = "리뷰 관련 API")
public interface ReviewApi {


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "리뷰 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-11-07 17:43:38",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "호출에 성공하였습니다.",
                                                 "data": {
                                                     "summary": {
                                                         "totalReviews": 2,
                                                         "averageRating": 3.0,
                                                         "fiveStarCount": 1,
                                                         "lowStarCount": 1
                                                     },
                                                     "reviews": [
                                                         {
                                                             "reviewId": 2,
                                                             "authorName": "이보호",
                                                             "content": "가지마세요 최악입니다",
                                                             "rating": 1,
                                                             "createdDate": "2025-11-07T17:43:21.650955"
                                                         },
                                                         {
                                                             "reviewId": 1,
                                                             "authorName": "김보호",
                                                             "content": "이것 정말 좋네요",
                                                             "rating": 5,
                                                             "createdDate": "2025-11-07T17:41:30.270951"
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
    })
    ResponseEntity<SuccessResponse<ReviewPageRes>> getReviews(
            ManagerPrincipal managerPrincipal, Integer rating, Pageable pageable
    );

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "리뷰 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": true,
                                                 "timestamp": "2025-11-07 19:38:36",
                                                 "code": "GLOBAL_200",
                                                 "httpStatus": 200,
                                                 "message": "호출에 성공하였습니다.",
                                                 "data": {
                                                     "reviewId": 1,
                                                     "authorName": "김보호",
                                                     "content": "이것 정말 좋네요",
                                                     "rating": 5,
                                                     "createdDate": "2025-11-07T17:41:30.270951"
                                                 }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "소속된 시설의 리뷰 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "timestamp": "2025-11-07 19:41:28",
                                                "code": "REVIEW_403_1",
                                                "httpStatus": 403,
                                                "message": "소속된 시설의 리뷰만 조작할 수 있습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리뷰 존재 X",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "isSuccess": false,
                                                 "timestamp": "2025-11-07 19:37:29",
                                                 "code": "REVIEW_404_1",
                                                 "httpStatus": 404,
                                                 "message": "해당 리뷰를 찾을 수 없습니다.",
                                                 "data": null
                                            }
                                            """
                            )
                    )
            ),
    })
    public ResponseEntity<SuccessResponse<ReviewInfoRes>> getReview(
            ManagerPrincipal managerPrincipal, Long reviewId
    );
}
