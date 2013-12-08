package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.ProlongationApplication;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.kofi.creditex.web.model.PriorApplicationForm;
import org.kofi.creditex.web.model.ProlongationApplicationForm;

import java.util.List;

public interface ApplicationService {
    List<Application> GetApplicationsByUsername(String username);
    List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username);
    List<PriorRepaymentApplication> GetPriorRepaymentApplicationsByUsername(String username);
    List<PriorApplicationForm> GetPriorApplicationFormsByUsername(String username);
    List<ProlongationApplication> GetProlongationApplicationsByUsername(String username);
    List<ProlongationApplicationForm> GetProlongationApplicationFormsByUsername(String username);
}
