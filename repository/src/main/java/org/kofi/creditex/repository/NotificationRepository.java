package org.kofi.creditex.repository;

import org.kofi.creditex.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface NotificationRepository extends JpaRepository<Notification, Long>, QueryDslPredicateExecutor<Notification> {
}
