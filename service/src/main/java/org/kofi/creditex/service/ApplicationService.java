package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.web.model.CreditApplicationForm;

import java.util.List;

public interface ApplicationService {
    List<CreditApplicationForm> TransformCreditApplications(List<Application> applications);
    List<Application> GetApplicationsByUsername(String username);
    List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username);
    CreditApplicationForm TransformCreditApplication(Application application);
}
