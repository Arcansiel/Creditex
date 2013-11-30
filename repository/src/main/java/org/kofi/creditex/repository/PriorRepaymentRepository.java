package org.kofi.creditex.repository;

import org.kofi.creditex.model.PriorRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 * UserCredentials: Constantine
 * Date: 29.11.13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public interface PriorRepaymentRepository extends JpaRepository<PriorRepayment, Integer>, QueryDslPredicateExecutor<PriorRepayment> {
}
