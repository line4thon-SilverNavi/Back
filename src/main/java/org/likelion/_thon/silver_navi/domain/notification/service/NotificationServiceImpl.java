package org.likelion._thon.silver_navi.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.repository.NotificationRepository;
import org.likelion._thon.silver_navi.domain.notification.web.dto.CountRes;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.likelion._thon.silver_navi.domain.notification.exception.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public CountRes getCount(User user) {
        int unreadCount = notificationRepository.countByUserAndIsReadFalse(user);
        return CountRes.from(unreadCount);
    }

    @Override
    @Transactional
    public void read(User user, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new NotificationAccessDeniedException();
        }
        notification.markAsRead();
    }
}
