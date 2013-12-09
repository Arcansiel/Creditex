package org.kofi.creditex.service;

import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.Payment;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class NewDayServiceImpl implements NewDayService {
    Date date;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CreditRepository creditRepository;

    @Override
    public void MarkExpired(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_ActiveAndCredit_OpenAndPaymentClosedAndPaymentEndLessThan(true,true,false,date);
        for (Payment payment:expiredPayments){
            payment.setPaymentExpired(true);
        }
        paymentRepository.save(expiredPayments);
    }

    @Override
    public void AddMainFine(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_ActiveAndCredit_OpenAndPaymentExpiredAndPaymentExpiredProcessed(true,true,true,false);
        for (Payment payment: expiredPayments){
            int newPayment = payment.getCredit().getMainFine()+payment.getRequiredPayment();
            payment.getCredit().setMainFine(newPayment);
            payment.setPaymentExpiredProcessed(true);
        }
        paymentRepository.save(expiredPayments);
    }

    @Override
    public void AddPercentFine() {
        List<Credit> credits = creditRepository.findByActiveAndOpenAndMainFineGreaterThan(true, true, 0);
        for (Credit credit :credits){
            int newFine = (int)Math.round(credit.getMainFine()*(credit.getProduct().getDebtPercent()*1.0)/100);
            credit.setPercentFine(credit.getPercentFine()+newFine);
        }
        creditRepository.save(credits);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onApplicationEvent(DateChangeEvent dateChangeEvent) {
        date = dateChangeEvent.getDate();
        AddMainFine();
        AddPercentFine();
        MarkExpired();
        // TODO Add required method calls
    }
}
