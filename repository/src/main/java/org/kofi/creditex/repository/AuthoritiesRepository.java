package org.kofi.creditex.repository;

import org.kofi.creditex.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AuthoritiesRepository extends JpaRepository<Authority, Long>, QueryDslPredicateExecutor<Authority> {
    Authority findByAuthority(String authority);
}
