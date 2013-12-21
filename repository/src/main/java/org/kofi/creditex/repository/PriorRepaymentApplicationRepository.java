package org.kofi.creditex.repository;

import org.kofi.creditex.model.PriorRepaymentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface PriorRepaymentApplicationRepository extends JpaRepository<PriorRepaymentApplication, Long>, QueryDslPredicateExecutor<PriorRepaymentApplication> {
    List<PriorRepaymentApplication> findByClient_Username(String username);
    PriorRepaymentApplication findByClientUsernameAndProcessed(String username, boolean processed);
    List<PriorRepaymentApplication> findByClientIdAndProcessed(long clientId, boolean processed);
}
