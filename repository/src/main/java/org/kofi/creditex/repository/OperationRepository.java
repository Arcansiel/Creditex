package org.kofi.creditex.repository;

import org.kofi.creditex.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface OperationRepository extends JpaRepository<Operation, Long>, QueryDslPredicateExecutor<Operation> {
}
