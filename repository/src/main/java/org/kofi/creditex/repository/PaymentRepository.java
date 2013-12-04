package org.kofi.creditex.repository;

import org.kofi.creditex.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * UserCredentials: Constantine
 * Date: 29.11.13
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer>, QueryDslPredicateExecutor<Payment> {
    public Iterable<Payment> findByCreditAndClosedAndStartLessThan(int credit, boolean closed, java.sql.Date date);
}
