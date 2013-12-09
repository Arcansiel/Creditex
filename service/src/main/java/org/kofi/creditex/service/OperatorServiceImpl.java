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
    UserRepository userRepository;

    @Override
    public User getUser(int user_id) {
        return userRepository.findOne(user_id);
    }

    @Override
    public Credit getCredit(int credit_id) {
        return creditRepository.findOne(credit_id);
    }

    @Override
    public Credit CurrentCredit(int client_id) {
        User client = userRepository.findOne(client_id);
        if(client == null){
            return null;
        }
        Credit credit = creditRepository.findOne(
                QCredit.credit.user.eq(client).and(QCredit.credit.currentMainDebt.gt(0))
        );
        return credit;
    }

    @Override
    public List<Operation> CreditOperations(int credit_id) {
        List<Operation> list = new ArrayList<Operation>();
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return list;
        }
        for(Operation op:operationRepository.findAll(
             QOperation.operation.credit.eq(credit)
                ,QOperation.operation.operationDate.desc()
        )){
            list.add(op);
        }
        return list;
    }

    private Iterable<Payment> CurrentPayments(Credit credit, Date now){
        return paymentRepository.findAll(
                QPayment.payment.credit.eq(credit)
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
        return CreditCalculator.TotalPaymentSum(CurrentPayments(credit,now),now,credit.getProduct().getDebtPercent());
    }

    @Override
    public int ExecuteOperation(int credit_id, Date now, OperationType type, int amount) {
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return -1;
        }
        //TODO
        //operation execution logic
        return -1;
    }
}
