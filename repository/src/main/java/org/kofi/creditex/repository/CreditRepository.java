package org.kofi.creditex.repository;

import org.kofi.creditex.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 * UserCredentials: Constantine
 * Date: 29.11.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public interface CreditRepository extends JpaRepository<Credit, Integer>, QueryDslPredicateExecutor<Credit> {
}
