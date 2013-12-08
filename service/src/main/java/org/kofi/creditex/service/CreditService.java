package org.kofi.creditex.service;

import org.kofi.creditex.model.Credit;
import org.kofi.creditex.web.model.CreditForm;

public interface CreditService {
    Credit GetCreditById(int id);
    CreditForm GetCreditFormById(int id);
}
