package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface SecurityService {
    List<Application> GetSecurityApplications();
    List<Credit> GetExpiredCredits(Date now);
    List<Credit> GetUnreturnedCredits(Date now);
    boolean ConfirmApplication(int security_id, int application_id, boolean confirmation, String comment);
    Application GetApplication(int id);
    List<Credit> GetCurrentClientCredits(int client_id);
    List<Credit> GetClientExpiredCredits(int client_id, Date now);
    List<Credit> GetClientUnreturnedCredits(int client_id, Date now);
    List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(int client_id);
    List<ProlongationApplication> GetClientProlongationApplications(int client_id);
}
