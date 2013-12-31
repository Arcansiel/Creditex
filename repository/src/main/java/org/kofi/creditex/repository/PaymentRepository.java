package org.kofi.creditex.repository;

import org.kofi.creditex.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.sql.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, QueryDslPredicateExecutor<Payment>, PaymentRepositoryExt {
    List<Payment> findByCredit_Id(long id);
    List<Payment> findByCredit_RunningAndPaymentClosedAndPaymentEndLessThan(boolean creditRunning,boolean closedPayment, Date dateNow);
    List<Payment> findByCredit_RunningAndPaymentExpiredAndPaymentExpiredProcessed(boolean creditRunning, boolean paymentExpired, boolean paymentExpiredProcessed);
}
