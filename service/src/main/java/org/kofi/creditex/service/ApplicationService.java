package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.web.model.CreditApplicationForm;

import java.util.List;

public interface ApplicationService {
    List<Application> GetApplicationsByUsername(String username);
    List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username);
}
