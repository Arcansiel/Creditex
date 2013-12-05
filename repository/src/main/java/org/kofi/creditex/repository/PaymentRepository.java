package org.kofi.creditex.repository;

import org.kofi.creditex.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.sql.Date;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, QueryDslPredicateExecutor<Payment> {
}
