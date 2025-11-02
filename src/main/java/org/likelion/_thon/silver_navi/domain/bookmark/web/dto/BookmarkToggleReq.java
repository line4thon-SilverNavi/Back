package org.likelion._thon.silver_navi.domain.bookmark.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.likelion._thon.silver_navi.domain.bookmark.entity.enums.BookmarkType;

@Getter
public class BookmarkToggleReq {
    @NotNull(message = "북마크 타입은 필수 항목입니다.")
    private BookmarkType type;

    @NotNull(message = "콘텐츠 ID는 필수 항목입니다.")
    private Long contentId;
}
