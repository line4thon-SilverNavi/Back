package org.likelion._thon.silver_navi.domain.notification.web.dto;

import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationStatus;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public record NotificationRes(
        Long notificationId,
        NotificationType type,
        NotificationStatus status,
        String targetName,
        String rejectReason,
        LocalDate programDate,      // 프로그램 일정
        LocalTime programStartTime, // 프로그램 시작 시간
        String createdAt,
        boolean isRead
) {
    public static NotificationRes from(
            Notification n,
            String targetName,
            String rejectReason,
            LocalDate programDate,
            LocalTime programStartTime
    ) {
        String formattedCreatedAt = formatCreatedAt(n.getCreatedAt());

        return new NotificationRes(
                n.getId(),
                n.getType(),
                n.getStatus(),
                targetName,
                rejectReason,
                programDate,
                programStartTime,
                formattedCreatedAt,
                n.isRead()
        );
    }

    public static String formatCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }
}
