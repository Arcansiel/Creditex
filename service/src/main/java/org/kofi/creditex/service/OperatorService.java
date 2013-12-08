package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface OperatorService {
    User getUser(int user_id);
    Credit getCredit(int credit_id);
    Credit CurrentCredit(int client_id);
    List<Operation> CreditOperations(int credit_id);
    int CurrentPayment(int credit_id, Date now);
    int ExecuteOperation(int credit_id, Date now, OperationType type, int amount);
}
