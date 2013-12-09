package org.kofi.creditex.service;

import org.kofi.creditex.model.Payment;
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

    @Override
    public void MarkExpired(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_ActiveAndCredit_OpenAndPaymentClosedAndPaymentEndLessThan(true,true,false,date);
        for (Payment payment:expiredPayments){
            payment.setPaymentExpired(true);
        }
        paymentRepository.save(expiredPayments);
    }

    @Override
    public void AddFine(){

        //TODO add logic
    }
    @Override
    public void onApplicationEvent(DateChangeEvent dateChangeEvent) {
        date = dateChangeEvent.getDate();
        AddFine();
        MarkExpired();
        // TODO Add required method calls
    }
}
