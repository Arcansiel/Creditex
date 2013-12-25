package org.kofi.creditex.repository;

import org.kofi.creditex.model.Credit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.sql.Date;
import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long>, QueryDslPredicateExecutor<Credit> {
    List<Credit> findByUserUsername(String username);
    Credit findByRunningAndUserUsername(boolean running, String username);
    List<Credit> findByRunningAndMainFineGreaterThan(boolean running, long minMainFine);
    List<Credit> findByUserIdAndRunning(long clientId, boolean running, Sort sort);
    long countByRunningAndExpiredAndUnreturnedAndCreditEndLessThan(boolean running, boolean expired, boolean unreturned, Date currentDate);
    List<Credit> findByRunningAndExpiredAndUnreturnedAndCreditEndLessThan(boolean running, boolean expired, boolean unreturned, Date currentDate);
}
