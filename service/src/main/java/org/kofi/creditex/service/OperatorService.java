package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface OperatorService {
    Credit getCredit(long credit_id);
    Credit CurrentCredit(long client_id);
    Payment CurrentPayment(long credit_id);
    List<Operation> CreditOperations(long credit_id);
    List<Payment> NearestPayments(long credit_id);
    int ExecuteOperation(String operator_name, long credit_id, OperationType type, long amount);
}
