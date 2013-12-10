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

    @Override
    public Payment CurrentPayment(int credit_id) {
        Date now = dateProvider.getCurrentSqlDate();
        return paymentRepository.findOne(
                QPayment.payment.credit.id.eq(credit_id)
                .and(QPayment.payment.paymentClosed.isFalse())
                .and(QPayment.payment.paymentStart.loe(now))
                .and(QPayment.payment.paymentEnd.gt(now))
        );
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
                .and(QPayment.payment.paymentClosed.eq(false))
                .and(QPayment.payment.paymentStart.lt(now))
                ,QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
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
        //TODO
        //operation execution logic

        return -3;
    }
}
