package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.*;
import org.kofi.creditex.web.model.CreditApplicationForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    public List<Application> GetApplicationsByUsername(String username) {
        return applicationRepository.findByClient_Username(username);
    }


    @Override
    public List<PriorRepaymentApplication> GetPriorRepaymentApplicationsByUsername(String username) {
        return priorRepaymentApplicationRepository.findByClient_Username(username);
    }

    @Override
    public List<ProlongationApplication> GetProlongationApplicationsByUsername(String username) {
        return prolongationApplicationRepository.findByClient_Username(username);
    }

    @Override
    public void RegisterApplicationByFormAndUsernameAndAccountManagerName(CreditApplicationForm form, String username, String accountManagerUsername) {
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        Product product = productRepository.findOne(form.getId());
        Application application = new Application()
                .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                .setAccountManager(accountManager)
                .setClient(client)
                .setDuration(form.getDuration())
                .setProduct(product)
                .setRequest(form.getRequestedMoney())
                .setVotingClosed(false)
                .setVoteAcceptance(0)
                .setVoteRejection(0);
        applicationRepository.save(application);
    }

    @Override
    public void RegisterPriorRepaymentApplicationByFormAndUsernameAndAccountManagerName(PriorRepaymentApplication form, String username, String accountManagerUsername){
        Credit credit=creditRepository.findByRunningAndUserUsername(true, username);
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        PriorRepaymentApplication application = new PriorRepaymentApplication()
                .setClient(client)
                .setAccountManager(accountManager)
                .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                .setCredit(credit)
                .setComment(form.getComment());
        priorRepaymentApplicationRepository.save(application);
    }

    @Override
    public void RegisterProlongationApplicationByFormAndUsernameAndAccountManagerName(ProlongationApplication form, String username, String accountManagerUsername){
        Credit credit=creditRepository.findByRunningAndUserUsername(true, username);
        User client = userRepository.findByUsername(username);
        User accountManager = userRepository.findByUsername(accountManagerUsername);
        ProlongationApplication application = new ProlongationApplication()
                .setClient(client)
                .setAccountManager(accountManager)
                .setCredit(credit)
                .setApplicationDate(creditexDateProvider.getCurrentSqlDate())
                .setDuration(form.getDuration())
                .setComment(form.getComment());
        prolongationApplicationRepository.save(application);
    }
}
