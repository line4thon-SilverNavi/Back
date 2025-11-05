package org.likelion._thon.silver_navi.domain.bookmark.service;

import org.likelion._thon.silver_navi.domain.program.web.dto.UserByProgramListRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

import java.util.List;

public interface ProgramBookmarkService {
    boolean toggle(User user, Long contentId);

    List<UserByProgramListRes> getBookmarks(User user);
}
