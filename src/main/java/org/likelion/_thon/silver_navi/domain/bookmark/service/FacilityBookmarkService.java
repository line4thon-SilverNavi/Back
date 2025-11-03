package org.likelion._thon.silver_navi.domain.bookmark.service;

import org.likelion._thon.silver_navi.domain.nursingfacility.web.dto.NearbyFacilityRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

import java.util.List;

public interface FacilityBookmarkService {
    boolean toggle(User user, Long contentId);

    List<NearbyFacilityRes> getBookmarks(User user);
}
