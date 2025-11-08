package org.likelion._thon.silver_navi.domain.notification.service;

import org.likelion._thon.silver_navi.domain.notification.web.dto.CountRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

public interface NotificationService {
    CountRes getCount(User user);
}
