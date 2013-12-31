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

    @Autowired
    DayReportService dayReportService;

    @Override
    public Credit getCredit(long credit_id) {
        return creditRepository.findOne(credit_id);
    }

    @Override
    public Credit CurrentCredit(long client_id) {
        return creditRepository.findOne(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.running.isTrue())
        );
    }

    private Payment CurrentPayment(long credit_id, Date now){
        return paymentRepository.findOne(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentStart.loe(now))
                        .and(QPayment.payment.paymentEnd.gt(now))
        );
    }

    @Override
    public Payment CurrentPayment(long credit_id) {
        return CurrentPayment(credit_id,dateProvider.getCurrentSqlDate());
    }

    @Override
    public List<Operation> CreditOperations(long credit_id) {
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
    public List<Payment> NearestPayments(long credit_id){
        Date now = dateProvider.getCurrentSqlDate();
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
                QPayment.payment.credit.id.eq(credit_id)
                        .and(QPayment.payment.paymentClosed.isFalse())
                        .and(QPayment.payment.paymentStart.loe(now))
                , QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
    }

    private boolean ExecuteWithdrawal(Credit credit, long amount){
        //OperationType.Withdrawal
        long money = credit.getCurrentMoney();
        if(money >= amount){
            money -= amount;
            credit.setCurrentMoney(money);
            creditRepository.save(credit);
            dayReportService.AddOutcome(amount);
            return true;
        }else{
            return false;//money < amount
        }
    }

    private void ExecuteDeposit(Credit credit, long amount){
        //OperationType.Withdrawal
        long money = credit.getCurrentMoney();
        credit.setCurrentMoney(money + amount);
        creditRepository.save(credit);
        dayReportService.AddIncome(amount);
    }

    public int ExecuteOperation(String operator_name, long credit_id, OperationType type, long amount) {
        if(amount <= 0){
            return -1;//amount <= 0
        }
        Date now = dateProvider.getCurrentSqlDate();
        User operator = userService.GetOperatorByUsername(operator_name);
        if(operator == null){
            return -2;//no operator
        }
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return -3;//no credit
        }
        if(!credit.isRunning()){
            return -4;//invalid credit state
        }
        if(type.equals(OperationType.Deposit)){
            //OperationType.Deposit
            ExecuteDeposit(credit, amount);
        }else{
            //OperationType.Withdrawal (IV)
            if(!ExecuteWithdrawal(credit, amount)){
                return -8;//money < amount
            }
        }

        Operation operation = new Operation();
        operation.setCredit(credit);
        operation.setAmount(amount);
        operation.setOperator(operator);
        operation.setType(type);
        operation.setOperationDate(now);
        operationRepository.save(operation);

        dayReportService.IncOperations();

        return 0;
    }



}
