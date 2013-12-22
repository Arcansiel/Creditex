package org.kofi.creditex.service;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
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

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public List<Application> GetSecurityUncheckedApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application app:applicationRepository.findAll(
                QApplication.application.securityAcceptance.eq(Acceptance.InProcess)
                .and(QApplication.application.acceptance.eq(Acceptance.InProcess)),
                QApplication.application.applicationDate.asc()
        )
                )
        {
             list.add(app);
        }
        return list;
    }

    private Application GetSecurityAssignedApplication(long security_id) {
        return applicationRepository.findOne(
                QApplication.application.securityAcceptance.eq(Acceptance.InProcess)
                        .and(QApplication.application.security.id.eq(security_id))
        );
    }

    @Override
    public int AssignApplicationToSecurity(long application_id, String security_name) {
        User security = userService.GetUserByUsername(security_name);
        if(security == null){ return -1; }//no security
        Application application = applicationRepository.findOne(application_id);
        if(application == null){
            return -2;//no application
        }
        if(!application.getAcceptance().equals(Acceptance.InProcess)
                || !application.getSecurityAcceptance().equals(Acceptance.InProcess)){
            return -3;//invalid application state
        }
        User applicationSecurity = application.getSecurity();
        if(applicationSecurity != null){
            if(applicationSecurity.getId() != security.getId()){
                return -4;//assigned to another security
            }else{
                return 1;//already assigned to this security
            }
        }
        Application current = GetSecurityAssignedApplication(security.getId());
        if(current != null){
            //cancel assignment of current application
            current.setSecurity(null);
            applicationRepository.save(current);
        }
        application.setSecurity(security);
        applicationRepository.save(application);
        return 0;
    }

    @Override
    public Application GetSecurityAssignedApplication(String security_name) {
        return applicationRepository.findOne(
                QApplication.application.securityAcceptance.eq(Acceptance.InProcess)
                .and(QApplication.application.security.username.eq(security_name))
        );
    }

    @Override
    public int CancelApplicationAssignment(long application_id, String security_name){
        User security = userService.GetUserByUsername(security_name);
        if(security == null){ return -1; }//no security
        Application application = applicationRepository.findOne(application_id);
        if(application == null){
            return -2;//no application
        }
        if(!application.getAcceptance().equals(Acceptance.InProcess)
                || !application.getSecurityAcceptance().equals(Acceptance.InProcess)){
            return -3;//invalid application state
        }
        User applicationSecurity = application.getSecurity();
        if(applicationSecurity != null){
            if(applicationSecurity.getId() != security.getId()){
                return -4;//assigned to another security
            }
        }else{
            return 1;//no security assigned to this application
        }
        application.setSecurity(null);
        applicationRepository.save(application);
        return 0;
    }

    @Override
    //текущие кредиты с просроченными платежами
    public List<Credit> GetExpiredCredits() {
        Ordering<Credit> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Credit, Date>() {
            public Date apply(Credit credit) {
                return credit.getLastNotificationDate();
            }
        });
        return ordering.sortedCopy(creditRepository.findAll(
                QCredit.credit.mainFine.gt(0)
        ));
    }

    @Override
    public List<Credit> GetUnreturnedCredits() {
        Date now = dateProvider.getCurrentSqlDate();
        Ordering<Credit> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Credit, Date>() {
            public Date apply(Credit credit) {
                return credit.getLastNotificationDate();
            }
        });
        return ordering.sortedCopy(creditRepository.findAll(
                QCredit.credit.creditEnd.lt(now)
                        .and(QCredit.credit.currentMainDebt.gt(0))
        ));
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
        if(security == null){ return -1; }//no security
        Application application = applicationRepository.findOne(application_id);
        if(application == null){
            return -2;//no application
        }
        if(!application.getAcceptance().equals(Acceptance.InProcess)
                || !application.getSecurityAcceptance().equals(Acceptance.InProcess)){
            return -3;//invalid application state
        }
        User applicationSecurity = application.getSecurity();
        if(applicationSecurity != null && applicationSecurity.getId() != security.getId()){
            return -4;//assigned to another security
        }
        application.setSecurityAcceptance(acceptance_value);
        application.setSecurityComment(comment);
        application.setSecurity(security);
        if(application.getRequest() < application.getProduct().getMinCommittee()){
             //no committee voting
            application.setAcceptance(acceptance_value);
        }
        if(!acceptance){
            application.setAcceptance(Acceptance.Rejected);
            application.setProcessed(true);//обработка заявки завершена, заявка отклонена
        }
        applicationRepository.save(application);
        return 0;
    }

    @Override
    public Application GetApplication(long id){
        return applicationRepository.findOne(id);
    }

    @Override
    public Credit GetCurrentClientCredit(long client_id) {
        return creditRepository.findOne(
                QCredit.credit.user.id.eq(client_id).and(QCredit.credit.running.isTrue())
        );
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
    public List<Credit> GetClientUnreturnedCredits(long client_id) {
        Date now = dateProvider.getCurrentSqlDate();
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c: creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id)
                .and(QCredit.credit.creditEnd.lt(now))
                .and(QCredit.credit.currentMainDebt.gt(0))
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

    @Override
    public int SendNotification(String security_name, long credit_id,
                                NotificationType notificationType, String message) {
        User security = userService.GetUserByUsername(security_name);
        if(security == null){ return -1; }//no security
        Credit credit = creditRepository.findOne(credit_id);
        if(credit == null){ return -2; }//no credit
        User client = credit.getUser();
        if(client == null){ return -3; }//no client
        if(!credit.isRunning()){ return -4; }//invalid credit state
        if(message == null){ message = ""; }
        Date date = dateProvider.getCurrentSqlDate();
        Notification notification = new Notification();
        notification.setNotificationDate(date);
        notification.setType(notificationType);
        notification.setMessage(message);
        notification.setSecurity(security);
        notification.setClient(client);
        notification.setCredit(credit);
        notificationRepository.save(notification);
        credit.setLastNotificationDate(date);
        creditRepository.save(credit);
        return 0;
    }

    @Override
    public long GetCreditNotificationsCount(long credit_id) {
        return notificationRepository.count(
                QNotification.notification.credit.id.eq(credit_id)
        );
    }
}
