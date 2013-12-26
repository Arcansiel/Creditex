package org.kofi.creditex.service;

import com.mysema.query.types.Predicate;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@Transactional
public class NewDayServiceImpl implements NewDayService {
    Date date;
    @Autowired
    private DayReportService dayReportService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CreditRepository creditRepository;

    @Override
    public void MarkExpired(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_RunningAndPaymentClosedAndPaymentEndLessThan(true,false,date);
        LinkedHashSet<Credit> expiredCredits = new LinkedHashSet<Credit>();
        for (Payment payment:expiredPayments){
            payment.setPaymentExpired(true);
            expiredCredits.add(payment.getCredit());
        }
        dayReportService.ExpiredCredits(expiredCredits.size());
        for(Credit credit: expiredCredits){
            credit.setExpired(true);
        }
        creditRepository.save(expiredCredits);
        paymentRepository.save(expiredPayments);
    }

    @Override
    public void MarkUnreturned() {
        List<Credit> unreturned = creditRepository.findByRunningAndExpiredAndUnreturnedAndCreditEndLessThan(true, true, false, date);
        dayReportService.UnReturnedCredits(unreturned.size());
        for(Credit credit : unreturned){
            credit.setUnreturned(true);
        }
        creditRepository.save(unreturned);
    }

    @Override
    public void AddMainFine(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_RunningAndPaymentExpiredAndPaymentExpiredProcessed(true,true,false);
        for (Payment payment: expiredPayments){
            long newPayment = payment.getCredit().getMainFine()+payment.getRequiredPayment();
            payment.getCredit().setMainFine(newPayment);
            payment.setPaymentExpiredProcessed(true);
        }
        paymentRepository.save(expiredPayments);
    }

    @Override
    public void AddPercentFine() {
        List<Credit> credits = creditRepository.findByRunningAndMainFineGreaterThan(true, 0);
        for (Credit credit :credits){
            int newFine = (int)Math.round(credit.getMainFine()*(credit.getProduct().getDebtPercent()*1.0)/100);
            credit.setPercentFine(credit.getPercentFine()+newFine);
        }
        creditRepository.save(credits);
    }

    @Override
    public void onApplicationEvent(DateChangeEvent dateChangeEvent) {
        dayReportService.SaveDayReport();
        date = dateChangeEvent.getDate();
        AddMainFine();
        AddPercentFine();
        MarkExpired();
        MarkUnreturned();
    }
}
