package org.kofi.creditex.service;

import org.kofi.creditex.model.DayReport;
import org.kofi.creditex.repository.DayReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
    public void RunningCredits(long count) {
        report.setRunningCredits(count);
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
        return dayReportRepository.findAll(new PageRequest(0,count, Sort.Direction.DESC, "dayDate")).getContent();
    }

    @PostConstruct
    private void DayReportInitialization(){
        report = dayReportRepository.findAll(new PageRequest(0,1, Sort.Direction.DESC, "dayDate")).getContent().get(0);
    }

    @Override
    public void IncClosedCredit() {
        report.setOverallClosedCredits(report.getOverallClosedCredits()+1);
    }
}