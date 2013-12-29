package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.ProlongationApplication;
import org.kofi.creditex.web.model.CreditApplicationRegistrationForm;

import java.util.List;

public interface ApplicationService {
    String RegisterApplicationByFormAndUsernameAndAccountManagerName(CreditApplicationRegistrationForm form, String username, String accountManagerUsername);
    boolean RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(PriorRepaymentApplication form, String username, String accountManagerUsername);
    boolean RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(ProlongationApplication form, String username, String accountManagerUsername);
    Application GetUnprocessedApplicationByUsername(String username);
    PriorRepaymentApplication GetUnprocessedPriorRepaymentApplicationByUsername(String username);
    ProlongationApplication GetUnprocessedProlongationApplicationByUsername(String username);
    void RemoveCreditApplicationWithId(long id);
    void RemoveProlongationApplicationWithId(long id);
    void RemovePriorRepaymentApplicationWithId(long id);


    Application GetCreditApplicationById(long id);
    long FinalizeCreditApplication(long id);
    ProlongationApplication GetProlongationApplicationById(long id);
    List<ProlongationApplication> GetProlongationApplicationsByClientIdAndProcessed(long clientId, boolean processed);
    List<PriorRepaymentApplication> GetPriorRepaymentApplicationByClientIdAndProcessed(long clientId, boolean processed);
    void FinalizeProlongationApplication(long id);
    PriorRepaymentApplication GetPriorRepaymentApplicationById(long id);
    void FinalizePriorRepaymentApplication(long id);
    List<Application> GetApplicationsByClientIdAndProcessed(long id, boolean processed);

}
