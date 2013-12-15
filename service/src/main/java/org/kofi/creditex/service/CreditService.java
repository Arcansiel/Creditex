package org.kofi.creditex.service;

import org.kofi.creditex.model.Credit;

import java.util.List;

public interface CreditService {
    Credit GetCreditById(long id);
    List<Credit> findByUsername(String username);
    Credit findByUsernameAndRunning(String username, boolean running);
}
