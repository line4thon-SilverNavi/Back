package org.likelion._thon.silver_navi.domain.notification.web.dto;

import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationStatus;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationType;

import java.time.LocalDate;
import java.time.LocalTime;

public record NotificationRes(
        Long notificationId,
        NotificationType type,
        NotificationStatus status,
        String targetName,
        String rejectReason,
        LocalDate programDate,      // 프로그램 일정
        LocalTime programStartTime, // 프로그램 시작 시간 (
        boolean isRead
) {
    public static NotificationRes from(
            Notification n,
            String targetName,
            String rejectReason,
            LocalDate programDate,
            LocalTime programStartTime
    ) {
        return new NotificationRes(
                n.getId(),
                n.getType(),
                n.getStatus(),
                targetName,
                rejectReason,
                programDate,
                programStartTime,
                n.isRead()
        );
    }
}
