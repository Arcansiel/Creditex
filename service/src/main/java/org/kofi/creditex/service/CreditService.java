package org.kofi.creditex.service;

import org.kofi.creditex.model.Credit;

import java.util.List;

public interface CreditService {
    Credit GetCreditById(long id);
    List<Credit> findByUsername(String username);
    Credit findByUsernameAndRunning(String username, boolean running);
    int ExecuteProlongation(long credit_id, long duration);
    List<Credit> GetCreditsByActive(boolean active);
    List<Credit> GetCreditsByUserId(long user_id);
    void PriorRepaymentClose(long creditId);
}
