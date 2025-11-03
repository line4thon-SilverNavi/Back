package org.likelion._thon.silver_navi.domain.search.web.controller;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;
import org.likelion._thon.silver_navi.domain.search.service.SearchService;
import org.likelion._thon.silver_navi.domain.search.web.dto.SearchResultRes;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/searchs")
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SuccessResponse<SearchResultRes>> search(
            @RequestParam @Size(min = 2, message = "검색어는 2글자 이상이어야 합니다.")
            String keyword,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SearchResultRes res = searchService.search(keyword, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
    }
}
