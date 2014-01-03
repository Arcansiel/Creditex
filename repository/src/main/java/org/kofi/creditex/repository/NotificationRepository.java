package org.kofi.creditex.repository;

import org.kofi.creditex.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, QueryDslPredicateExecutor<Notification> {
    List<Notification> findByClientIdAndViewed(long clientId, boolean viewed);
    List<Notification> findByClientIdAndViewed(long clientId, boolean viewed, Pageable page);

}
