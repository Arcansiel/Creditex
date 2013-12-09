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

    @Override
    public Credit getCredit(int credit_id) {
        return creditRepository.findOne(credit_id);
    }

    @Override
    public Credit CurrentCredit(int client_id) {
        //TODO use 'active' field
        Credit credit = creditRepository.findOne(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.currentMainDebt.gt(0))
        );
        return credit;
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

    private Iterable<Payment> CurrentPayments(int credit_id, Date now){
        return paymentRepository.findAll(
                QPayment.payment.credit.id.eq(credit_id)
                .and(QPayment.payment.paymentClosed.eq(false))
                .and(QPayment.payment.paymentStart.lt(now))
        );
    }

    @Override
    public int[] CurrentPayment(int credit_id, Date now) {
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return null;
        }
        return CreditCalculator.TotalPaymentSum(CurrentPayments(credit_id,now),now,credit.getProduct().getDebtPercent());
    }

    @Override
    public int ExecuteOperation(String operator_name, int credit_id, Date now, OperationType type, int amount) {
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
