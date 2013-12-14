package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.ProlongationApplication;
import org.kofi.creditex.web.model.CreditApplicationForm;

import java.util.List;

public interface ApplicationService {
    List<Application> GetApplicationsByUsername(String username);
    List<PriorRepaymentApplication> GetPriorRepaymentApplicationsByUsername(String username);
    List<ProlongationApplication> GetProlongationApplicationsByUsername(String username);
    void RegisterApplicationByFormAndUsernameAndAccountManagerName(CreditApplicationForm form, String username, String accountManagerUsername);
    void RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(PriorRepaymentApplication form, String username, String accountManagerUsername);
    void RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(ProlongationApplication form, String username, String accountManagerUsername);
}
