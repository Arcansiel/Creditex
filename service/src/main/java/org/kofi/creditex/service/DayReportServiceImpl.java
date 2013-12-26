package org.kofi.creditex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.kofi.creditex.model.DayReport;
import org.kofi.creditex.repository.DayReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service("dayReportService")
@DependsOn("entityManagerFactory")
public class DayReportServiceImpl implements DayReportService {
    @Autowired
    private DayReportRepository dayReportRepository;
    private DayReport report;
    @Override
    public void IncCredit() {
        report.setOverallCredits(report.getOverallCredits()+1);
        report.setCredits(report.getCredits() + 1);
    }

    @Override
    public void IncCreditApplication() {
        report.setOverallCreditApplications(report.getOverallCreditApplications()+1);
        report.setCreditApplications(report.getCreditApplications()+1);
    }

    @Override
    public void IncPriorApplication() {
        report.setOverallPriorRepaymentApplications(report.getOverallProlongationApplications()+1);
        report.setPriorRepaymentApplications(report.getPriorRepaymentApplications()+1);
    }

    @Override
    public void IncProlongationApplication() {
        report.setOverallProlongationApplications(report.getOverallProlongationApplications()+1);
        report.setProlongationApplications(report.getProlongationApplications()+1);
    }

    @Override
    public void IncProduct() {
        report.setProducts(report.getProducts()+1);
    }

    @Override
    public void IncClient() {
        report.setClients(report.getClients()+1);
    }

    @Override
    public void IncAccountManager() {
        report.setAccountManagers(report.getAccountManagers()+1);
    }

    @Override
    public void IncSecurityManager() {
        report.setSecurityManagers(report.getSecurityManagers()+1);
    }

    @Override
    public void IncOperationManager() {
        report.setOperationManagers(report.getOperationManagers()+1);
    }

    @Override
    public void IncCommitteeManager() {
        report.setCommitteeManagers(report.getCommitteeManagers()+1);
    }

    @Override
    public void IncOperations() {
        report.setOperations(report.getOperations()+1);
    }

    @Override
    public void ExpiredCredits(long count) {
        report.setOverallExpiredCredits(report.getOverallExpiredCredits()+count);
        report.setExpiredCredits(count);
    }

    @Override
    public void UnReturnedCredits(long count) {
        report.setOverallUnreturnedCredits(report.getOverallUnreturnedCredits()+count);
        report.setUnreturnedCredits(count);
    }

    @Override
    public void AddIncome(long income) {
        report.setIncome(report.getIncome()+income);
    }

    @Override
    public void AddOutcome(long outcome) {
        report.setOutcome(report.getOutcome()+outcome);
    }

    @Override
    public DayReport SaveDayReport() {
        DayReport forward = new DayReport();
        dayReportRepository.save(report);
        forward
                .setDayDate(report.getDayDate().plusDays(1))
                .setCurrentBankMoney(report.getCurrentBankMoney())
                .setOverallCreditApplications(report.getOverallCreditApplications())
                .setOverallCredits(report.getOverallCredits())
                .setOverallExpiredCredits(report.getOverallExpiredCredits())
                .setOverallPriorRepaymentApplications(report.getOverallPriorRepaymentApplications())
                .setOverallProlongationApplications(report.getOverallProlongationApplications())
                .setOverallUnreturnedCredits(report.getOverallUnreturnedCredits());
        report = forward;
        return report;
    }

    @Override
    public DayReport FindLatestReport() {
        return dayReportRepository.findAll(new PageRequest(0,1, Sort.Direction.DESC, "dayDate")).getContent().get(0);
    }

    @Override
    public List<DayReport> GetLatestReportList(int count) {
        int currentCount = (int) dayReportRepository.count();
        int actualCount = count>currentCount?currentCount:count;
        Ordering<DayReport> reportOrdering = Ordering.natural().onResultOf(new Function<DayReport, LocalDate>() {
            @Override
            public LocalDate apply(DayReport dayReport) {
                return dayReport.getDayDate();
            }
        });
        return reportOrdering.sortedCopy(dayReportRepository.findAll(new PageRequest(0, actualCount, Sort.Direction.DESC, "dayDate")).getContent());
    }

    @PostConstruct
    private void DayReportInitialization(){
        log.warn("Initializing DayReportService!");
        report = dayReportRepository.findAll(new PageRequest(0,1, Sort.Direction.DESC, "dayDate")).getContent().get(0);
    }

    @Override
    public void IncClosedCredit() {
        report.setOverallClosedCredits(report.getOverallClosedCredits()+1);
    }

    @Override
    public String GetLatestReportListInJson(int count) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JodaModule module = new JodaModule();
        mapper.registerModule(module);
        return mapper.writeValueAsString(GetLatestReportList(count));
    }

    @Override
    public String ConvertReportListToJson(List<DayReport> list) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JodaModule module = new JodaModule();
        mapper.registerModule(module);
        return mapper.writeValueAsString(list);
    }
}
