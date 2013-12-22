package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface SecurityService {
    List<Application> GetSecurityUncheckedApplications();
    int AssignApplicationToSecurity(long application_id, String security_name);
    Application GetSecurityAssignedApplication(String security_name);
    int CancelApplicationAssignment(long application_id, String security_name);
    List<Credit> GetExpiredCredits();
    List<Credit> GetUnreturnedCredits();
    int ConfirmApplication(String security_name, long application_id, boolean confirmation, String comment);
    Application GetApplication(long id);
    Credit GetCurrentClientCredit(long client_id);
    List<Credit> GetClientExpiredCredits(long client_id);
    List<Credit> GetClientUnreturnedCredits(long client_id);
    List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(long client_id);
    List<ProlongationApplication> GetClientProlongationApplications(long client_id);
    int SendNotification(String security_name, long credit_id,
                         NotificationType notificationType, String message);
    long GetCreditNotificationsCount(long credit_id);
}
