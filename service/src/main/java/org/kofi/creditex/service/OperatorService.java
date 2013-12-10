package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface OperatorService {
    Credit getCredit(int credit_id);
    Credit CurrentCredit(int client_id);
    Payment CurrentPayment(int credit_id);
    List<Operation> CreditOperations(int credit_id);
    List<Payment> NearestPayments(int credit_id);
    int ExecuteOperation(String operator_name, int credit_id, OperationType type, int amount);
}
