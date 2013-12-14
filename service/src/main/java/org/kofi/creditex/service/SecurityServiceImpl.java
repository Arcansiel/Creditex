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
    PaymentRepository paymentRepository;

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
                QApplication.application.securityAcceptance.eq(Acceptance.InProcess),
                QApplication.application.applicationDate.asc()
        )
                )
        {
             list.add(app);
        }
        return list;
    }

    private  void f(){

    }

    @Override
    //текущие кредиты с просроченными платежами
    public List<Credit> GetExpiredCredits() {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.mainFine.gt(0),
                QCredit.credit.creditStart.desc()
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
            QCredit.credit.creditEnd.lt(now)
                .and(QCredit.credit.currentMainDebt.gt(0))
                ,QCredit.credit.creditStart.desc()
        )){
             list.add(c);
        }
        return list;
    }

    @Override
    public int ConfirmApplication(String security_name, long application_id, boolean acceptance, String comment){
        Acceptance acceptance_value;
        if (acceptance){
            acceptance_value = Acceptance.Accepted;
        }else{
            acceptance_value = Acceptance.Rejected;
        }
        User security = userService.GetUserByUsername(security_name);
        if(security == null){ return -1; }
        Application application = applicationRepository.findOne(application_id);
        if(application == null || !application.getSecurityAcceptance().equals(Acceptance.InProcess)){
            return -2;
        }
        application.setSecurityAcceptance(acceptance_value);
        application.setSecurityComment(comment);
        application.setSecurity(security);
        if(application.getRequest() < application.getProduct().getMinCommittee()){
             //no committee voting
            application.setAcceptance(acceptance_value);
        }
        applicationRepository.save(application);
        return 0;
    }

    @Override
    public Application GetApplication(long id){
        return applicationRepository.findOne(id);
    }

    @Override
    public List<Credit> GetCurrentClientCredits(long client_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.running.isTrue()),
                QCredit.credit.creditStart.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    //все кредиты клиента с хотябы одним просроченным платежом
    public List<Credit> GetClientExpiredCredits(long client_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id)
                        .and(QCredit.credit.payments.any().paymentExpired.isTrue()),
                QCredit.credit.creditStart.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    public long GetClientPaymentsCount(long client_id) {
        return paymentRepository.count(
                QPayment.payment.credit.user.id.eq(client_id)
                .and(QPayment.payment.paymentClosed.isTrue())
        );
    }

    @Override
    public long GetClientExpiredPaymentsCount(long client_id) {
        return paymentRepository.count(
                QPayment.payment.credit.user.id.eq(client_id)
                        .and(QPayment.payment.paymentExpired.isTrue())
        );
    }

    @Override
    public List<Credit> GetClientUnreturnedCredits(long client_id) {
        Date now = dateProvider.getCurrentSqlDate();
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c: creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id)
                .and(QCredit.credit.creditEnd.lt(now))
                .and(QCredit.credit.originalMainDebt.gt(0))
                ,QCredit.credit.creditStart.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    public List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(long client_id) {
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
    public List<ProlongationApplication> GetClientProlongationApplications(long client_id) {
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
