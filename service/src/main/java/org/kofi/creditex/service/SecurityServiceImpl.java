package org.kofi.creditex.service;

import org.kofi.creditex.model.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
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
    UserRepository userRepository;

    //@Autowired PriorRepaymentApplicationRepository priorRepaymentApplicationRepository;

    //@Autowired ProlongationApplicationRepository prolongationApplicationRepository;

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
    public List<Credit> GetExpiredCredits(Date now) {
        List<Credit> list = new ArrayList<Credit>();
        //TODO

        return list;
    }

    @Override
    public List<Credit> GetUnreturnedCredits(Date now) {
        List<Credit> list = new ArrayList<Credit>();
        //TODO

        return list;
    }

    @Override
    public boolean ConfirmApplication(int security_id, int application_id, boolean acceptance, String comment){
        User security = userRepository.findOne(security_id);
        if(security == null){ return false; }
        Application application = applicationRepository.findOne(application_id);
        if(application == null || application.getSecurityAcceptance() != null){
            return false;
        }
        application.setSecurityAcceptance(acceptance);
        application.setSecurityComment(comment);
        application.setSecurity(security);
        //TODO
        //it will change data in database
        //applicationRepository.save(application);

        return true;
    }

    @Override
    public Application GetApplication(int id){
        return applicationRepository.findOne(id);
    }

    @Override
    public List<Credit> GetCurrentClientCredits(int client_id) {
        List<Credit> list = new ArrayList<Credit>();
        User client = userRepository.findOne(client_id);
        if(client == null){
            return list;
        }
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.eq(client).and(QCredit.credit.currentMainDebt.gt(0)),
                QCredit.credit.start.desc()
        )){
            list.add(c);
        }
        return list;
    }

    @Override
    public List<Credit> GetClientExpiredCredits(int client_id, Date now) {
        List<Credit> list= new ArrayList<Credit>();
        User client = userRepository.findOne(client_id);
        if(client == null){
            return list;
        }
        //TODO

        return list;
    }

    @Override
    public List<Credit> GetClientUnreturnedCredits(int client_id, Date now) {
        List<Credit> list= new ArrayList<Credit>();
        User client = userRepository.findOne(client_id);
        if(client == null){
            return list;
        }
        //TODO

        return list;
    }

    @Override
    public List<PriorRepaymentApplication> GetClientPriorRepaymentApplications(int client_id) {
        List<PriorRepaymentApplication> list= new ArrayList<PriorRepaymentApplication>();
        User client = userRepository.findOne(client_id);
        if(client == null){
            return list;
        }
        //TODO
        //QPriorRepaymentApplication.priorRepaymentApplication.client.eq(client)

        return list;
    }

    @Override
    public List<ProlongationApplication> GetClientProlongationApplications(int client_id) {
        List<ProlongationApplication> list = new ArrayList<ProlongationApplication>();
        User client = userRepository.findOne(client_id);
        if(client == null){
            return list;
        }
        //TODO
        //QProlongationApplication.prolongationApplication.client.eq(client)

        return list;
    }
}
