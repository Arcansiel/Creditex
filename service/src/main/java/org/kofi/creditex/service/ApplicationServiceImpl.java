package org.kofi.creditex.service;

import org.kofi.creditex.model.Application;
import org.kofi.creditex.repository.ApplicationRepository;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Override
    public List<CreditApplicationForm> TransformCreditApplications(List<Application> applications) {

        List<CreditApplicationForm> result = new ArrayList<CreditApplicationForm>(applications.size());
        for(Application application:applications){
            result.add(TransformCreditApplication(application));

        }
        return result;
    }
    @Override
    public CreditApplicationForm TransformCreditApplication(Application application){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String acceptance = "";
        String whoRejected = "";
        String whyRejected = "";
        if (application.getAcceptance()==null)
            acceptance = "В рассмотрении";
        else {
            if (application.getAcceptance())
                acceptance = "Принята";
            else {
                acceptance = "Отклонена";
                if (!application.getSecurityAcceptance()){
                    whoRejected="Служба безопасности";
                    whyRejected=application.getSecurityComment();
                }
                else {
                    if(!application.getCommitteeAcceptance()){
                        whoRejected="Кредитный комитет";
                        whyRejected=application.getVoteAcceptance()+"/"+application.getVoteRejection();
                    }
                    else {
                        whoRejected = "Глава кредитного отдела";
                        whyRejected = application.getHeadComment();
                    }
                }

            }

        }
        return new CreditApplicationForm()
                .setId(application.getId())
                .setProductName(application.getProduct().getName())
                .setProductId(application.getProduct().getId())
                .setRequestedMoney(application.getRequest())
                .setDuration(application.getDuration())
                .setApplicationDate(df.format(application.getApplicationDate()))
                .setAcceptance(acceptance)
                .setWhoRejected(whoRejected)
                .setWhyRejected(whyRejected);
    }

    @Override
    public List<Application> GetApplicationsByUsername(String username) {
        return applicationRepository.findByClient_Username(username);
    }
    @Override
    public List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username){
        return TransformCreditApplications(GetApplicationsByUsername(username));
    }
}
