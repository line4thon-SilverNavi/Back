package org.likelion._thon.silver_navi.domain.bookmark.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.bookmark.service.FacilityBookmarkService;
import org.likelion._thon.silver_navi.domain.bookmark.service.ProgramBookmarkService;
import org.likelion._thon.silver_navi.domain.bookmark.web.dto.BookmarkToggleReq;
import org.likelion._thon.silver_navi.domain.bookmark.web.dto.BookmarkToggleRes;
import org.likelion._thon.silver_navi.global.auth.security.CustomUserDetails;
import org.likelion._thon.silver_navi.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final ProgramBookmarkService programBookmarkService;
    private final FacilityBookmarkService facilityBookmarkService;

    @PostMapping
    public ResponseEntity<SuccessResponse<BookmarkToggleRes>> toggleBookmark(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid BookmarkToggleReq req
    ) {
        boolean isOn = switch (req.getType()) {
            case PROGRAM -> programBookmarkService.toggle(userDetails.getUser(), req.getContentId());
            case FACILITY -> facilityBookmarkService.toggle(userDetails.getUser(), req.getContentId());
        };

        BookmarkToggleRes res = new BookmarkToggleRes(isOn ? "on" : "off");
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(res));
    }

//    @GetMapping
//    public ResponseEntity<SuccessResponse<IntegratedSearchRes>> getBookmark(){
//
//        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.from(res));
//    }
}
