package org.kofi.creditex.service;

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
    public void PayBill(){
        List<Payment> payments = paymentRepository.findReadyToPayBills(date);
        for(Payment payment: payments){
            dayReportService.IncBankMoney(payment.getPercents());
            payment.getCredit().setCurrentMainDebt(payment.getCredit().getCurrentMainDebt() - payment.getRequiredPayment() + payment.getPercents());
            payment.getCredit().setCurrentPercentDebt(payment.getCredit().getCurrentPercentDebt() - payment.getPercents());
            payment.getCredit().setCurrentMoney(payment.getCredit().getCurrentMoney()-payment.getRequiredPayment());
            payment.setPaymentClosed(true);
        }
        paymentRepository.save(payments);
    }

    @Override
    public void PayFine(){
        List<Payment> expiredPayments = paymentRepository.findReadyToPayFine();
        LinkedHashSet<Credit> expiredCredits = new LinkedHashSet<>();
        for (Payment payment : expiredPayments) {
            dayReportService.IncBankMoney(payment.getPercents());
            payment.setPaymentClosed(true);
            expiredCredits.add(payment.getCredit());
        }
        for (Credit credit: expiredCredits){
            credit.setCurrentMoney(credit.getCurrentMoney() - credit.getMainFine() - credit.getPercentFine());
            dayReportService.IncBankMoney(credit.getPercentFine());
            credit.setMainFine(0);
            credit.setPercentFine(0);
        }
        paymentRepository.save(expiredPayments);
    }


    @Override
    public void CloseCredits(){
        List<Credit> creditsToClose = creditRepository.findCreditsToClose(date);
        for (Credit credit : creditsToClose){
            credit.setRunning(false);
            dayReportService.IncBankMoney(credit.getOriginalMainDebt());
        }
        creditRepository.save(creditsToClose);
    }

    @Override
    public void MarkExpired(){
        List<Payment> expiredPayments = paymentRepository.findByCredit_RunningAndPaymentClosedAndPaymentEndLessThan(true,false,date);
        LinkedHashSet<Credit> expiredCredits = new LinkedHashSet<>();
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
        CloseCredits();
        PayFine();
        PayBill();
        AddMainFine();
        AddPercentFine();
        MarkExpired();
        MarkUnreturned();
    }
}
