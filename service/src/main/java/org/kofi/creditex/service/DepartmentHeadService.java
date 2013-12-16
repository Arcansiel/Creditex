package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;

public interface DepartmentHeadService {
    List<Application> GetCommitteeApprovedUncheckedApplications(boolean approved);
    List<Application> GetCommitteeVoteApplications();
    Application GetApplicationById(long id);
    List<Vote> GetApplicationVotes(long application_id);
    int SetApplicationHeadApproved(long application_id, String head_username, boolean acceptance, String comment);
    List<ProlongationApplication> GetUncheckedProlongations();
    ProlongationApplication GetProlongation(long id);
    int SetProlongationApproved(long prolongation_id, boolean acceptance);
    List<PriorRepaymentApplication> GetUncheckedPriors();
    PriorRepaymentApplication GetPrior(long id);
    int SetPriorApproved(long prior_id, boolean acceptance);
}
