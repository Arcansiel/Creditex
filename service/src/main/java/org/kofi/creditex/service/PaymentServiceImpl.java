package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    CreditexDateProvider dateProvider;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    DayReportService dayReport;


    private Payment CurrentPayment(Credit credit, Date now){
        return paymentRepository.findOne(
                QPayment.payment.credit.eq(credit)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentStart.loe(now))
                        .and(QPayment.payment.paymentEnd.gt(now))
        );
    }

    private List<Payment> ExpiredPayments(Credit credit, Date now){
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
                QPayment.payment.credit.eq(credit)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentExpired.isTrue())
                , QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
    }

    private void ProcessDayCredit(Credit credit, Date now){

        //обработка просроченных платежей
        List<Payment> payments = ExpiredPayments(credit, now);
        long amount, mainDebt, percents;
        //long totalIncome = 0;
        for(Payment p:payments){
            amount = p.getRequiredPayment();
            if(credit.getCurrentMoney() >= amount){
                percents = p.getPercents();
                mainDebt = amount - percents;
                credit.setCurrentMoney(credit.getCurrentMoney() - amount);
                credit.setCurrentMainDebt(credit.getCurrentMainDebt() - mainDebt);
                credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - percents);
                credit.setMainFine(credit.getMainFine() - amount);
                p.setPaymentClosed(true);
                //totalIncome += amount;
            }else{
                //break;//TODO - break or process next payments with next.amount < current.amount and possible next.amount <= credit.money ???
            }
        }
        //просроченные платежи обработаны

        //обработка пени за просроченные платежи
        if(credit.getCurrentMoney() > 0 && credit.getPercentFine() > 0){
            //обработка начисленной пени за просроченные платежи
            if(credit.getCurrentMoney() >= credit.getPercentFine()){
                //выплата всей суммы пени
                credit.setCurrentMoney(credit.getCurrentMoney() - credit.getPercentFine());
                //totalIncome += credit.getPercentFine();
                credit.setPercentFine(0);
            }else{
                //выплата части суммы пени
                credit.setPercentFine(credit.getPercentFine() - credit.getCurrentMoney());
                //totalIncome += credit.getCurrentMoney();
                credit.setCurrentMoney(0);
            }
        }
        //обработка пени завершена

        //обработка текущего (непросроченного) платежа
        if(credit.getCurrentMoney() > 0){
            Payment current = CurrentPayment(credit, now);
            if(current != null){
                amount = current.getRequiredPayment();
                if(credit.getCurrentMoney() >= amount){
                    percents = current.getPercents();
                    mainDebt = amount - percents;
                    credit.setCurrentMoney(credit.getCurrentMoney() - amount);
                    credit.setCurrentMainDebt(credit.getCurrentMainDebt() - mainDebt);
                    credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - percents);
                    current.setPaymentClosed(true);
                    payments.add(current);//to save all list of payments
                    //totalIncome += amount;
                }//else - not enough money for current payment
            }//no current payment
        }//no more money
        //обработка текущего платежа завершена

        //проверка, закрыт ли кредит
        if(credit.getCurrentMainDebt() <= 0 && credit.getPercentFine() <= 0){
            //основной долг погашен полностью
            //пеня за просроченные платежи погашена полностью
            credit.setRunning(false);
        }

        //сохранение в базу данных
        creditRepository.save(credit);
        paymentRepository.save(payments);
        //dayReport.AddIncome(totalIncome);
    }

    @Override
    public void ProcessDayPayments() {
        Date now = dateProvider.getCurrentSqlDate();
        for(Credit credit:creditRepository.findAll(QCredit.credit.running.isTrue())){
            ProcessDayCredit(credit, now);
        }
    }
}
