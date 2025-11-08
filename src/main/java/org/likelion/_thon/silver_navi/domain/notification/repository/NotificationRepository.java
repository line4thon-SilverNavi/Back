package org.likelion._thon.silver_navi.domain.notification.repository;

import org.likelion._thon.silver_navi.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
