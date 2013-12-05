package org.kofi.creditex.repository;

import org.kofi.creditex.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface CreditRepository extends JpaRepository<Credit, Integer>, QueryDslPredicateExecutor<Credit> {
}
