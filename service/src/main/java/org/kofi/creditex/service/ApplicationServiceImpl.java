package org.kofi.creditex.service;

import org.joda.time.LocalDate;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.*;

import org.kofi.creditex.web.model.CreditApplicationRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PriorRepaymentApplicationRepository priorRepaymentApplicationRepository;
    @Autowired
    private ProlongationApplicationRepository prolongationApplicationRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired    
    private CreditexDateProvider creditexDateProvider;
    @Autowired
    private CreditService creditService;
    @Autowired
    private DayReportService dayReportService;

    @Override
    public String RegisterApplicationByFormAndUsernameAndAccountManagerName(CreditApplicationRegistrationForm form, String username, String accountManagerUsername) {
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        Product product = productRepository.findOne(form.getProductId());
        boolean minDur = form.getDuration()>=product.getMinDuration();
        boolean maxDur = form.getDuration()<=product.getMaxDuration();
        boolean minMon = form.getRequest()>=product.getMinMoney();
        boolean maxMon = form.getRequest()<=product.getMaxMoney();
        if (minDur && maxDur && minMon && maxMon)
        {
            Application application = new Application()
                    .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                    .setAccountManager(accountManager)
                    .setClient(client)
                    .setDuration(form.getDuration())
                    .setProduct(product)
                    .setRequest(form.getRequest())
                    .setAcceptance(Acceptance.InProcess)
                    .setSecurityAcceptance(Acceptance.InProcess)
                    .setCommitteeAcceptance(Acceptance.InProcess)
                    .setHeadAcceptance(Acceptance.InProcess)
                    .setVotingClosed(false)
                    .setVoteAcceptance(0)
                    .setVoteRejection(0);
            applicationRepository.save(application);
            dayReportService.IncCreditApplication();
            return null;
        }
        if (!minDur)
            return "Длительность кредита недостаточна для выбранного кредитного продукта";
        if(!maxDur)
            return "Длительность кредита превышает макслимльную для выбранного кредитного продукта";
        if (!minMon)
            return "Денежная сумма кредита недостаточна для выбранного кредитного продукта";
        return "Денежная сумма превышает максимальную для выбранного продукта";
    }

    @Override
    public boolean RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(PriorRepaymentApplication form, String username, String accountManagerUsername){
        Credit credit=creditRepository.findByRunningAndUserUsername(true, username);
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        PriorRepaymentApplication application = new PriorRepaymentApplication()
                .setClient(client)
                .setAccountManager(accountManager)
                .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                .setAcceptance(Acceptance.InProcess)
                .setCredit(credit)
                .setComment(form.getComment());
        priorRepaymentApplicationRepository.save(application);
        dayReportService.IncPriorApplication();
        return true;
    }

    @Override
    public boolean RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(ProlongationApplication form, String username, String accountManagerUsername){
        Credit credit=creditRepository.findByRunningAndUserUsername(true, username);
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        ProlongationApplication application = new ProlongationApplication()
                .setClient(client)
                .setAccountManager(accountManager)
                .setCredit(credit)
                .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                .setAcceptance(Acceptance.InProcess)
                .setDuration(form.getDuration())
                .setComment(form.getComment());
        prolongationApplicationRepository.save(application);
        dayReportService.IncProlongationApplication();
        return true;
    }

    @Override
    public Application GetUnprocessedApplicationByUsername(String username) {
        return applicationRepository.findByClientUsernameAndProcessed(username, false);
    }

    @Override
    public PriorRepaymentApplication GetUnprocessedPriorRepaymentApplicationByUsername(String username) {
        return priorRepaymentApplicationRepository.findByClientUsernameAndProcessed(username,false);
    }

    @Override
    public ProlongationApplication GetUnprocessedProlongationApplicationByUsername(String username) {
        return prolongationApplicationRepository.findByClientUsernameAndProcessed(username,false);
    }

    @Override
    public void RemovePriorRepaymentApplicationWithId(long id) {
        PriorRepaymentApplication application = priorRepaymentApplicationRepository.findOne(id);
        application.setProcessed(true);
        application.setAcceptance(Acceptance.Aborted);
        priorRepaymentApplicationRepository.save(application);
    }

    @Override
    public void RemoveCreditApplicationWithId(long id) {
        Application application = applicationRepository.findOne(id);
        application.setProcessed(true);
        application.setAcceptance(Acceptance.Aborted);
        applicationRepository.save(application);
    }

    @Override
    public void RemoveProlongationApplicationWithId(long id) {
        ProlongationApplication application = prolongationApplicationRepository.findOne(id);
        application.setProcessed(true);
        application.setAcceptance(Acceptance.Aborted);
        prolongationApplicationRepository.save(application);
    }

    @Override
    public Application GetCreditApplicationById(long id) {
        return applicationRepository.findOne(id);
    }

    @Override
    public long FinalizeCreditApplication(long id) {
        Application application = applicationRepository.findOne(id);
        application.setProcessed(true);
        applicationRepository.save(application);
        LocalDate date = creditexDateProvider.getCurrentDate();
        Date end = creditexDateProvider.transformDate(date.plusMonths((int) application.getDuration()));
        long[] params = new long[3];
        List<Payment> payments = CreditCalculator.PaymentPlan(application,creditexDateProvider.getCurrentSqlDate(),params);
        Credit credit = new Credit()
                .setCreditApplication(application)
                .setUser(application.getClient())
                .setRunning(true)
                .setCreditStart(creditexDateProvider.getCurrentSqlDate())
                .setCreditEnd(end)
                .setCurrentMainDebt(application.getRequest())
                .setCurrentMoney(application.getRequest())
                .setDuration(application.getDuration())
                .setOriginalMainDebt(application.getRequest())
                .setProduct(application.getProduct())
                .setPayments(payments)
                .setCurrentPercentDebt(params[1]);
        for (Payment payment : credit.getPayments()){
            payment.setCredit(credit);
        }
        Credit saved = creditRepository.save(credit);
        dayReportService.IncCredit();
        return saved.getId();
    }

    @Override
    public ProlongationApplication GetProlongationApplicationById(long id) {
        return prolongationApplicationRepository.findOne(id);
    }

    @Override
    public List<ProlongationApplication> GetProlongationApplicationsByClientIdAndProcessed(long clientId, boolean processed) {
        return prolongationApplicationRepository.findByClientIdAndProcessed(clientId, processed);
    }

    @Override
    public void FinalizeProlongationApplication(long id) {
        ProlongationApplication application = prolongationApplicationRepository.findOne(id);
        application.setProcessed(true);
        prolongationApplicationRepository.save(application);
        creditService.ExecuteProlongation(application.getCredit().getId(), application.getDuration());
    }

    @Override
    public PriorRepaymentApplication GetPriorRepaymentApplicationById(long id) {
        return priorRepaymentApplicationRepository.findOne(id);
    }

    @Override
    public void FinalizePriorRepaymentApplication(long id) {
        PriorRepaymentApplication application = priorRepaymentApplicationRepository.findOne(id);
        application.setProcessed(true);
        priorRepaymentApplicationRepository.save(application);
        creditService.PriorRepaymentClose(application.getCredit().getId());
    }

    @Override
    public List<PriorRepaymentApplication> GetPriorRepaymentApplicationByClientIdAndProcessed(long clientId, boolean processed) {
        return priorRepaymentApplicationRepository.findByClientIdAndProcessed(clientId, processed);
    }

    @Override
    public List<Application> GetApplicationsByClientIdAndProcessed(long id, boolean processed) {
        return applicationRepository.findByClientIdAndProcessed(id, processed);
    }
}
