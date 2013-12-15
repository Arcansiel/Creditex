package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.ProlongationApplication;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.kofi.creditex.web.model.CreditApplicationRegistrationForm;

import java.util.List;

public interface ApplicationService {
    List<Application> GetApplicationsByUsername(String username);
    List<PriorRepaymentApplication> GetPriorRepaymentApplicationsByUsername(String username);
    List<ProlongationApplication> GetProlongationApplicationsByUsername(String username);
    String RegisterApplicationByFormAndUsernameAndAccountManagerName(CreditApplicationRegistrationForm form, String username, String accountManagerUsername);
    boolean RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(PriorRepaymentApplication form, String username, String accountManagerUsername);
    boolean RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(ProlongationApplication form, String username, String accountManagerUsername);
    Application GetUnprocessedApplicationByUsername(String username);
    PriorRepaymentApplication GetUnprocessedPriorRepaymentApplicationByUsername(String username);
    ProlongationApplication GetUnprocessedProlongationApplicationByUsername(String username);
    void RemoveCreditApplicationWithId(long id);
    void RemoveProlongationApplicationWithId(long id);
    void RemovePriorRepaymentApplicationWithId(long id);

}
