package org.likelion._thon.silver_navi.domain.notification.service;

import org.likelion._thon.silver_navi.domain.notification.web.dto.CountRes;
import org.likelion._thon.silver_navi.domain.notification.web.dto.NotificationRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;

import java.util.List;

public interface NotificationService {
    CountRes getCount(User user);

    void read(User user, Long notificationId);

    List<NotificationRes> getList(User user);
}
