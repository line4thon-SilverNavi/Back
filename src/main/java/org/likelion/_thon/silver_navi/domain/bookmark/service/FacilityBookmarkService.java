package org.likelion._thon.silver_navi.domain.bookmark.service;

import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface FacilityBookmarkService {
    boolean toggle(User user, Long contentId);

}
