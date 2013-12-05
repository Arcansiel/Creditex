package org.kofi.creditex.repository;

import org.kofi.creditex.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ApplicationRepository extends JpaRepository<Application, Integer>, QueryDslPredicateExecutor<Application> {
}
