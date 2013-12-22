package org.kofi.creditex.service;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * Сервис для работы с кредитами
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService{
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditexDateProvider dateProvider;

    /**
     * Получить кредит по ID
     * @param id Значение Id требуемого кредита
     * @return Кредит
     */
    @Override
    public Credit GetCreditById(long id) {
        Credit credit = creditRepository.findOne(id);
        Ordering<Payment> paymentOrdering = Ordering.natural().onResultOf(new Function<Payment, Long>() {
            @Nullable
            @Override
            public Long apply(Payment payment) {
                return payment.getNumber();
            }
        });
        credit.setPayments(paymentOrdering.sortedCopy(credit.getPayments()));
        return credit;
    }

    @Override
    public List<Credit> findByUsername(String username) {
        return creditRepository.findByUserUsername(username);
    }

    @Override
    public int ExecuteProlongation(long credit_id, long duration) {

        if(duration <= 0){
            return -1;//invalid duration
        }
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return -2;//no credit
        }
        if(!credit.isRunning()){
            return -3;//invalid credit state
        }

        //TODO test prolongation logic

        //сдвинуть дату конца кредита
        LocalDate localDate = new LocalDate(credit.getCreditEnd().getTime()).plusMonths((int)duration);
        credit.setCreditEnd(new Date(localDate.toDate().getTime()));

        //сдвинуть даты платежей по кредиту
        List<Payment> payments =
                Prolongation(GetCreditPaymentsToProlongation(credit_id), (int)duration);

        creditRepository.save(credit);
        paymentRepository.save(payments);

        return 0;
    }

    private List<Payment> GetCreditPaymentsToProlongation(long credit_id){
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentExpired.isFalse())//TODO ? prolongation and expired payments
        )){
            list.add(p);
        }
        return list;
    }

    private List<Payment> Prolongation(List<Payment> payments, int duration){
        LocalDate localDate;
        for(Payment payment:payments){
            //paymentEnd
            localDate = new LocalDate(payment.getPaymentEnd().getTime()).plusMonths((int)duration);
            payment.setPaymentEnd(new Date(localDate.toDate().getTime()));
            //paymentStart
            localDate = new LocalDate(payment.getPaymentStart().getTime()).plusMonths((int)duration);
            payment.setPaymentStart(new Date(localDate.toDate().getTime()));
        }
        return payments;
    }

    @Override
    public List<Credit> GetCreditsByActive(boolean active) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit credit:creditRepository.findAll(
                QCredit.credit.running.eq(active)
                ,QCredit.credit.creditStart.desc()
        )){
            list.add(credit);
        }
        return  list;
    }

    @Override
    public List<Credit> GetCreditsByUserId(long user_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit credit:creditRepository.findAll(
                QCredit.credit.user.id.eq(user_id)
                ,QCredit.credit.creditStart.desc()
        )){
            list.add(credit);
        }
        return  list;
    }

    @Override
    public Credit findByUsernameAndRunning(String username, boolean running) {
        return creditRepository.findByRunningAndUserUsername(running, username);
    }

    @Override
    public void PriorRepaymentClose(long creditId) {
        Credit credit = creditRepository.findOne(creditId);
        long paymentNumber=0;
        for (Payment payment : credit.getPayments()){
            payment.setPaymentClosed(true);
            paymentNumber = payment.getNumber();
        }
        long modifiedPercentDebt = 0;
        if (credit.getProduct().getPrior()== PriorRepayment.AvailableFineInterest)
            modifiedPercentDebt = Math.round(credit.getCurrentMainDebt() * ((credit.getProduct().getPercent()+credit.getProduct().getPriorRepaymentPercent()) /100));
        else
            if (credit.getProduct().getPrior() == PriorRepayment.AvailableFinePercentSum)
                modifiedPercentDebt = Math.round(credit.getCurrentPercentDebt() * (credit.getProduct().getPriorRepaymentPercent()/100));
        long totalSum = credit.getCurrentMainDebt() + modifiedPercentDebt;
        LocalDate start = dateProvider.getCurrentDate();
        LocalDate end = start.plusMonths(1);
        Date startSql = dateProvider.transformDate(start);
        Date endSql = dateProvider.transformDate(end);
        Payment payment = new Payment()
                .setCredit(credit)
                .setNumber(paymentNumber+1)
                .setPaymentStart(startSql)
                .setPaymentEnd(endSql)
                .setRequiredPayment(totalSum)
                .setPercents(modifiedPercentDebt);
        paymentRepository.save(payment);
    }


    @Override
    public List<Credit> GetCreditsByUserIdAndRunning(long userId, boolean running) {
        return creditRepository.findByUserIdAndRunning(userId, running, new Sort(Sort.Direction.ASC, "creditStart"));
    }
}
