package org.kofi.creditex.operations;

import java.sql.Date;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class CreditOperations {

    /*
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    //GET INFORMATION

    public Iterable<Credit> getCurrentCredits(User user){
        return creditRepository.findByUser_credentialsAndDebtGreaterThan(user.id(), 0);
    }

    public Iterable<Payment> getCurrentPayments(Credit credit, Date now){
        return paymentRepository.findByCreditAndClosedAndStartLessThan(credit.id(),false,now);
    }*/

    /*public int getCurrentPaymentsSum(Credit credit, Date now){
        Iterable<Payment> current = getCurrentPayments(credit, now);

        return 0;
    }*/

    //OPERATIONS


}
