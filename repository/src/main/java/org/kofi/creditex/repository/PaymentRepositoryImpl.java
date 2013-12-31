package org.kofi.creditex.repository;

import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import org.kofi.creditex.model.Payment;
import org.kofi.creditex.model.QPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
public class PaymentRepositoryImpl implements PaymentRepositoryExt {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public List<Payment> findReadyToPayBills(Date currentDate) {
        QPayment payment = QPayment.payment;
        Predicate search = payment.paymentClosed.eq(false)
                .and(payment.paymentEnd.goe(currentDate))
                .and(payment.paymentStart.loe(currentDate))
                .and(payment.credit.currentMoney.goe(payment.requiredPayment));
        return Lists.newArrayList(paymentRepository.findAll(search));
    }

    @Override
    public List<Payment> findReadyToPayFine() {
        QPayment payment = QPayment.payment;
        Predicate search = payment.paymentClosed.eq(false)
                .and(payment.paymentExpired.eq(true))
                .and(payment.credit.currentMoney.goe(payment.credit.mainFine.add(payment.credit.percentFine)));
        return Lists.newArrayList(paymentRepository.findAll(search));
    }
}
