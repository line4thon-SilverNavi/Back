package org.likelion._thon.silver_navi.domain.notification.repository;

import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    int countByUserAndIsReadFalse(User user);

    List<Notification> findAllByUserOrderByCreatedAtDesc(User user);
}
