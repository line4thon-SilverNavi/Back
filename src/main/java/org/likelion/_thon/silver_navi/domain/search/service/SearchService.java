package org.likelion._thon.silver_navi.domain.search.service;

import org.likelion._thon.silver_navi.domain.search.web.dto.SearchResultRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface SearchService {
    SearchResultRes search(String keyword, User user);
}
