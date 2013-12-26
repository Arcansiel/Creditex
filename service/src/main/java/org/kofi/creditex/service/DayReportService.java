package org.kofi.creditex.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.kofi.creditex.model.DayReport;

import java.util.List;

public interface DayReportService {
    void IncCredit();
    void IncCreditApplication();
    void IncPriorApplication();
    void IncProlongationApplication();
    void IncProduct();
    void IncClient();
    void IncClosedCredit();
    void IncAccountManager();
    void IncSecurityManager();
    void IncOperationManager();
    void IncCommitteeManager();
    void IncOperations();
    void ExpiredCredits(long count);
    void UnReturnedCredits(long count);
    DayReport SaveDayReport();
    DayReport FindLatestReport();
    List<DayReport> GetLatestReportList(int count);
    void AddIncome(long income);
    void AddOutcome(long outcome);
    String GetLatestReportListInJson(int count) throws JsonProcessingException;
}
