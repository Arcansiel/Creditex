package org.kofi.creditex.repository;

import org.kofi.creditex.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>, QueryDslPredicateExecutor<Application> {
    List<Application> findByClient_Username(String username);
    Application findByClientUsernameAndProcessed(String username, boolean processed);
    List<Application> findByClientIdAndProcessed(long clientId, boolean processed);
}
