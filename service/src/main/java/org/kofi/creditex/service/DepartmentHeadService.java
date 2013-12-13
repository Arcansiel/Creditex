package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;

public interface DepartmentHeadService {
    List<Application> GetCommitteeApprovedUncheckedApplications(boolean approved);
    List<Application> GetCommitteeVoteApplications();
    Application GetApplicationById(int id);
    List<Vote> GetApplicationVotes(int application_id);
    int SetApplicationHeadApproved(int application_id, String head_username, boolean acceptance, String comment);
    List<ProlongationApplication> GetUncheckedProlongations();
    ProlongationApplication GetProlongation(int id);
    int SetProlongationApproved(int prolongation_id, boolean acceptance);
}
