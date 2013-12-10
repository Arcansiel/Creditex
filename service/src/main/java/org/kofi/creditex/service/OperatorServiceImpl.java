package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserService userService;

    @Autowired
    CreditexDateProvider dateProvider;

    @Override
    public Credit getCredit(int credit_id) {
        return creditRepository.findOne(credit_id);
    }

    @Override
    public Credit CurrentCredit(int client_id) {
        return creditRepository.findOne(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.running.isTrue())
        );
    }

    private Payment CurrentPayment(int credit_id, Date now){
        return paymentRepository.findOne(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentStart.loe(now))
                        .and(QPayment.payment.paymentEnd.gt(now))
        );
    }

    @Override
    public Payment CurrentPayment(int credit_id) {
        return CurrentPayment(credit_id,dateProvider.getCurrentSqlDate());
    }

    @Override
    public List<Operation> CreditOperations(int credit_id) {
        List<Operation> list = new ArrayList<Operation>();
        for(Operation op:operationRepository.findAll(
             QOperation.operation.credit.id.eq(credit_id)
                ,QOperation.operation.operationDate.desc()
        )){
            list.add(op);
        }
        return list;
    }

    @Override
    public List<Payment> NearestPayments(int credit_id){
        Date now = dateProvider.getCurrentSqlDate();
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentStart.lt(now))
                , QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
    }

    private List<Payment> ExpiredPayments(int credit_id){
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentExpired.isTrue())
                , QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
    }

    private void ExecutePaymentExpired(Credit credit){
        //оплата просроченных платежей (I)
        List<Payment> expired = ExpiredPayments(credit.getId());
        int mainDebt = 0;
        int percents = 0;
        for(Payment p:expired){
            percents += p.getPercents();
            mainDebt += (p.getRequiredPayment() - p.getPercents());
            p.setPaymentClosed(true);
        }
        paymentRepository.save(expired);
        credit.setMainFine(0);
        credit.setPercentFine(0);
        credit.setCurrentMainDebt(credit.getCurrentMainDebt() - mainDebt);
        credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - percents);
        creditRepository.save(credit);
    }

    private void ExecutePaymentCurrent(Credit credit, Payment current){
        //оплата текущего платежа (II)
        current.setPaymentClosed(true);
        paymentRepository.save(current);
        credit.setCurrentMainDebt(credit.getCurrentMainDebt() - (current.getRequiredPayment() - current.getPercents()));
        credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - current.getPercents());
        creditRepository.save(credit);
    }

    private boolean ExecuteWithdrawal(Credit credit, int amount){
        //OperationType.Withdrawal (III)
        int money = credit.getCurrentMoney();
        if(money >= amount){
            money -= amount;
            credit.setCurrentMoney(money);
            creditRepository.save(credit);
            return true;
        }else{
            return false;//money < amount
        }
    }

    @Override
    public int ExecuteOperation(String operator_name, int credit_id, OperationType type, int amount) {
        Date now = dateProvider.getCurrentSqlDate();
        User operator = userService.GetUserByUsername(operator_name);
        if(operator == null){
            return -1;
        }
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return -2;
        }

        if(type.equals(OperationType.Deposit)){
            //OperationType.Deposit
            Payment current = CurrentPayment(credit_id, now);
            if(current == null){
                int payment_sum = credit.getMainFine();
                if(payment_sum > 0){
                    int sum = payment_sum + credit.getPercentFine();
                    if(sum == amount){
                        //оплата просроченных платежей (I)
                        ExecutePaymentExpired(credit);
                    }else{
                        return -3;//sum != amount
                    }
                }else{
                    return 1;//no operations available
                }
            }else{
                int need = current.getRequiredPayment();
                if(need == amount){
                    //оплата текущего платежа (II)
                    ExecutePaymentCurrent(credit, current);
                }else{
                    return -4;//need != amount
                }
            }
        }else{
            //OperationType.Withdrawal (III)
            if(!ExecuteWithdrawal(credit, amount)){
                return -5;//money < amount
            }
        }

        Operation operation = new Operation();
        operation.setCredit(credit);
        operation.setAmount(amount);
        operation.setOperator(operator);
        operation.setType(type);
        operation.setOperationDate(now);
        operationRepository.save(operation);

        return 0;
    }
}
