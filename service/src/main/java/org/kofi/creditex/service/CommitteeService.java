package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public interface CommitteeService {
    Application GetApplication(int application_id);
    List<Vote> GetApplicationVotes(int application_id);
    List<Application> GetVotingApplications(boolean voting);
    int Vote(String committee_name, int application_id, boolean acceptance, String comment);
    List<Credit> GetClientCredits(int client_id);
}
