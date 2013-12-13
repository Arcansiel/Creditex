package org.kofi.creditex.repository;

import org.kofi.creditex.model.ProlongationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface ProlongationApplicationRepository extends JpaRepository<ProlongationApplication, Long>, QueryDslPredicateExecutor<ProlongationApplication> {
    List<ProlongationApplication> findByClient_Username(String username);
}
