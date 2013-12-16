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
    PriorRepaymentApplicationRepository priorRepository;

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

    /*
    private PriorRepaymentApplication CurrentPrior(long credit_id){
        return priorRepository.findOne(
                QPriorRepaymentApplication.priorRepaymentApplication.credit.id.eq(credit_id)
                .and(QPriorRepaymentApplication.priorRepaymentApplication.processed.isFalse())
                .and(QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.Accepted))
        );
    }

    //amount[0] - debt + fine, amount[1] - fine

    public PriorRepaymentApplication CurrentPriorRepayment(long credit_id, long[] amount) {
        PriorRepaymentApplication prior = CurrentPrior(credit_id);
        if(prior != null && amount != null && amount.length > 0){
            long[] x = PriorRepaymentAmount(prior.getCredit());
            if(x != null){
                amount[0] = x[0];//debt + fine
                if(amount.length > 1){
                    amount[1] = x[1];//fine
                }
            }else{
                amount[0] = -1;//prior repayment not available
            }
        }
        return prior;
    }*/

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
                        .and(QPayment.payment.paymentStart.lt(now))
                , QPayment.payment.paymentStart.asc()
        )){
            list.add(p);
        }
        return list;
    }

    private List<Payment> ExpiredPayments(long credit_id){
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

    private boolean ExecutePaymentExpired(Credit credit, long amount){
        //оплата просроченных платежей (I)
        long sum = credit.getMainFine() + credit.getPercentFine();
        if(sum == amount){
            List<Payment> expired = ExpiredPayments(credit.getId());
            long mainDebt = 0;
            long percents = 0;
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
            if(credit.getCurrentMainDebt() <= 0){
                credit.setRunning(false);//close credit
            }
            creditRepository.save(credit);
            return true;
        }else{
            return false;
        }
    }

    //[0] = debt + fine, [1] - fine
    private long[] PriorRepaymentAmount(Credit credit){
        long amount, fine;
        switch(credit.getProduct().getPrior()){
            case AvailableFinePercentSum:
                fine = Math.round(
                        (double)credit.getCurrentPercentDebt()
                                * (credit.getProduct().getPriorRepaymentPercent() / 100)
                ) ;
                amount = credit.getCurrentMainDebt() + fine;
                break;
            case AvailableFineInterest:
                fine = Math.round(
                        (double)credit.getCurrentMainDebt()
                                * ((credit.getProduct().getPercent() + credit.getProduct().getPriorRepaymentPercent()) / 1200)
                );
                amount = credit.getCurrentMainDebt() + fine;
                break;
            case Available:
                fine = 0;
                amount = credit.getCurrentMainDebt();
                break;
            default:
                return null;
        }
        return new long[]{ amount, fine };
    }

    private void CloseCreditPayments(long credit_id){
        List<Payment> list = new ArrayList<Payment>();
        for(Payment p:paymentRepository.findAll(
            QPayment.payment.credit.id.eq(credit_id)
                .and(QPayment.payment.paymentClosed.isFalse())
        )){
            p.setPaymentClosed(true);
            list.add(p);
        }
        paymentRepository.save(list);
    }

    private boolean ExecutePriorRepayment(Credit credit, PriorRepaymentApplication prior, long amount){
        //досрочное погашение кредита (II)
        long[] x = PriorRepaymentAmount(credit);
        if(x == null){
            return false;//prior repayment not available
        }
        if(x[0] == amount){
            prior.setProcessed(true);
            credit.setCurrentMainDebt(0);
            credit.setRunning(false);//close credit
            credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - x[1]);
            priorRepository.save(prior);
            creditRepository.save(credit);
            CloseCreditPayments(credit.getId());
            return true;
        }else{
            return false;//sum != amount
        }
    }

    private boolean ExecutePaymentCurrent(Credit credit, Payment current, long amount){
        //оплата текущего платежа (III)
        if(current.getRequiredPayment() == amount){
            current.setPaymentClosed(true);
            credit.setCurrentMainDebt(credit.getCurrentMainDebt() - (current.getRequiredPayment() - current.getPercents()));
            credit.setCurrentPercentDebt(credit.getCurrentPercentDebt() - current.getPercents());
            if(credit.getCurrentMainDebt() <= 0){
                credit.setRunning(false);//close credit
            }
            paymentRepository.save(current);
            creditRepository.save(credit);
            return true;
        }else{
            return false;
        }
    }

    private boolean ExecuteWithdrawal(Credit credit, long amount){
        //OperationType.Withdrawal (IV)
        long money = credit.getCurrentMoney();
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
    public int ExecuteOperation(String operator_name, long credit_id, OperationType type, long amount) {
        if(amount <= 0){
            return -10;//amount <= 0
        }
        Date now = dateProvider.getCurrentSqlDate();
        User operator = userService.GetUserByUsername(operator_name);
        if(operator == null){
            return -20;//no operator
        }
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){
            return -30;//no credit
        }

        if(type.equals(OperationType.Deposit)){
            //OperationType.Deposit
            if(credit.getMainFine() > 0){
                //оплата просроченных платежей (I) в первую очередь
                if(!ExecutePaymentExpired(credit, amount)){
                    return -1;//sum != amount
                }
            }else{
                /*PriorRepaymentApplication prior = CurrentPrior(credit_id);
                if(prior != null){
                    //досрочное погашение кредита (II)
                    if(!ExecutePriorRepayment(credit, prior, amount)){
                        return -2;//sum != amount
                    }
                }else{*/
                    Payment current = CurrentPayment(credit_id, now);
                    if(current != null){
                        //оплата текущего платежа (III)
                        if(!ExecutePaymentCurrent(credit, current, amount)){
                            return -3;//sum != amount
                        }
                    }else{
                        return 1;//no payments available now
                    }
                //}
            }
        }else{
            //OperationType.Withdrawal (IV)
            if(!ExecuteWithdrawal(credit, amount)){
                return -4;//money < amount
            }
        }

        Operation operation = new Operation();
        operation.setCredit(credit);
        operation.setAmount(amount);
        operation.setOperator(operator);
        operation.setType(type);
        operation.setOperationDate(now);
        operationRepository.save(operation);

        return 0;//operation executed
    }
}
