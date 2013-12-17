package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface CommitteeService {
    Application GetApplication(long application_id);
    List<Vote> GetApplicationVotes(long application_id);
    List<Application> GetApplicationsForVoting();
    List<Application> GetVotingClosedApplications();
    int Vote(String committee_name, long application_id, boolean acceptance, String comment);
}
