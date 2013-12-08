package org.kofi.creditex.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.kofi.creditex.model.Application;
import org.kofi.creditex.repository.ApplicationRepository;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Override
    public List<Application> GetApplicationsByUsername(String username) {
        return applicationRepository.findByClient_Username(username);
    }
    @Override
    public List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username){
        Function<Application, CreditApplicationForm> transformFunction = new Function<Application, CreditApplicationForm>() {
            @Nullable
            @Override
            public CreditApplicationForm apply(@Nullable Application application) {
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
        };
        return Lists.transform(GetApplicationsByUsername(username), transformFunction);
    }
}
