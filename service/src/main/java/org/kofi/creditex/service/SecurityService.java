package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface SecurityService {
    List<Application> GetSecurityApplications();
    List<Credit> GetExpiredCredits();
    List<Credit> GetUnreturnedCredits();
    boolean ConfirmApplication(String security_name, long application_id, boolean confirmation, String comment);
    Application GetApplication(long id);
    List<Credit> GetCurrentClientCredits(long client_id);
    List<Credit> GetClientExpiredCredits(long client_id);
    List<Credit> GetClientUnreturnedCredits(long client_id);
    List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(long client_id);
    List<ProlongationApplication> GetClientProlongationApplications(long client_id);
}
