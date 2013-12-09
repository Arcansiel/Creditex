package org.kofi.creditex.repository;

import org.kofi.creditex.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer>, QueryDslPredicateExecutor<Credit> {
    List<Credit> findByUserUsername(String username);
    Credit findByActiveAndOpenAndUserUsername(boolean active,boolean open, String username);
    List<Credit> findByActiveAndOpenAndMainFineGreaterThan(boolean active, boolean open, int minMainFine);
}
