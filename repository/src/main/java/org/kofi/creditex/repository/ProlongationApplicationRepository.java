package org.kofi.creditex.repository;

import org.kofi.creditex.model.ProlongationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface ProlongationApplicationRepository extends JpaRepository<ProlongationApplication, Long>, QueryDslPredicateExecutor<ProlongationApplication> {
    ProlongationApplication findByClientUsernameAndProcessed(String username, boolean processed);
    List<ProlongationApplication> findByClientIdAndProcessed(long clientId, boolean processed);
}
