package org.kofi.creditex.service;

import org.kofi.creditex.model.*;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    UserService userService;

    @Autowired
    PriorRepaymentApplicationRepository priorRepaymentApplicationRepository;

    @Autowired
    ProlongationApplicationRepository prolongationApplicationRepository;

    @Autowired
    CreditexDateProvider dateProvider;

    @Override
    public List<Application> GetSecurityApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application app:applicationRepository.findAll(
                QApplication.application.securityAcceptance.isNull(),
                QApplication.application.applicationDate.asc()
        )
                )
        {
             list.add(app);
        }
        return list;
    }

    @Override
    //текущие кредиты с просроченными платежами
    public List<Credit> GetExpiredCredits() {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.mainFine.gt(0),
                QCredit.credit.start.desc()
        )){
             list.add(c);
        }
        return list;
    }

    @Override
    public List<Credit> GetUnreturnedCredits() {
        Date now = dateProvider.getCurrentSqlDate();
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c: creditRepository.findAll(
            QCredit.credit.end.lt(now)
                .and(QCredit.credit.originalMainDebt.gt(0))
                ,QCredit.credit.start.desc()
        )){
             list.add(c);
        }
        return list;
    }

    @Override
    public boolean ConfirmApplication(String security_name, int application_id, boolean acceptance, String comment){
        User security = userService.GetUserByUsername(security_name);
        if(security == null){ return false; }
        Application application = applicationRepository.findOne(application_id);
        if(application == null || application.getSecurityAcceptance() != null){
            return false;
        }
        application.setSecurityAcceptance(acceptance);
        application.setSecurityComment(comment);
        application.setSecurity(security);
        applicationRepository.save(application);
        return true;
    }

    @Override
    public Application GetApplication(int id){
        return applicationRepository.findOne(id);
    }

    @Override
    public List<Credit> GetCurrentClientCredits(int client_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.running.isTrue()),
                QCredit.credit.start.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    //все кредиты клиента с хотябы одним просроченным платежом
    public List<Credit> GetClientExpiredCredits(int client_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id)
                        .and(QCredit.credit.payments.any().paymentExpired.isTrue()),
                QCredit.credit.start.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    public List<Credit> GetClientUnreturnedCredits(int client_id) {
        Date now = dateProvider.getCurrentSqlDate();
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c: creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id)
                .and(QCredit.credit.end.lt(now))
                .and(QCredit.credit.originalMainDebt.gt(0))
                ,QCredit.credit.start.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    public List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(int client_id) {
        List<PriorRepaymentApplication> list= new ArrayList<PriorRepaymentApplication>();
        for(PriorRepaymentApplication a:priorRepaymentApplicationRepository.findAll(
                QPriorRepaymentApplication.priorRepaymentApplication.client.id.eq(client_id),
                QPriorRepaymentApplication.priorRepaymentApplication.applicationDate.desc()
        )){
             list.add(a);
        }
        return list;
    }

    @Override
    public List<ProlongationApplication> GetClientProlongationApplications(int client_id) {
        List<ProlongationApplication> list = new ArrayList<ProlongationApplication>();
        for(ProlongationApplication a:prolongationApplicationRepository.findAll(
                QProlongationApplication.prolongationApplication.client.id.eq(client_id),
                QProlongationApplication.prolongationApplication.applicationDate.desc()
        )){
            list.add(a);
        }
        return list;
    }
}
