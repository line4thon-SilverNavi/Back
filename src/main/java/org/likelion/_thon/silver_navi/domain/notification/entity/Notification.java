package org.likelion._thon.silver_navi.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationStatus;
import org.likelion._thon.silver_navi.domain.notification.entity.enums.NotificationType;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;        // 알림 유형

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private NotificationStatus status;    // 프로그램/상담 상태 변경 시 "승인" 또는 "거부"

    @Column(nullable = false)
    private Long referenceId;             // 알림 관련 대상 (예: 프로그램 ID)

    @Column(nullable = false)
    private boolean isRead = false;       // 읽음 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //프로그램 상태 변경 시
    public static Notification createProgramStatusChanged(User user, Long programId, boolean isApproved) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.PROGRAM)
                .status(isApproved ? NotificationStatus.APPROVED : NotificationStatus.REJECTED)
                .referenceId(programId)
                .isRead(false)
                .build();
    }

    //상담 상태 변경 시
    public static Notification createConsultStatusChanged(User user, Long consultId, boolean isApproved) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.CONSULT)
                .status(isApproved ? NotificationStatus.APPROVED : NotificationStatus.REJECTED)
                .referenceId(consultId)
                .isRead(false)
                .build();
    }

    //리뷰 답변 시
    public static Notification createReviewReply(User user, Long reviewId) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.REVIEW_REPLY)
                .status(null)
                .referenceId(reviewId)
                .isRead(false)
                .build();
    }
}
