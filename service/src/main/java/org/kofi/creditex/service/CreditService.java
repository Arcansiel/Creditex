package org.kofi.creditex.service;

import org.kofi.creditex.model.Credit;
import org.kofi.creditex.web.model.CreditForm;

public interface CreditService {
    Credit GetCreditById(long id);
    CreditForm GetCreditFormById(long id);
}
