package org.likelion._thon.silver_navi.domain.search.service;

import org.likelion._thon.silver_navi.global.web.dto.IntegratedSearchRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface SearchService {
    IntegratedSearchRes search(String keyword, User user);
}
