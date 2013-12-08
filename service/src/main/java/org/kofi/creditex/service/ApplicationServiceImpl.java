package org.kofi.creditex.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.kofi.creditex.model.Application;
import org.kofi.creditex.model.PriorRepaymentApplication;
import org.kofi.creditex.model.ProlongationApplication;
import org.kofi.creditex.repository.ApplicationRepository;
import org.kofi.creditex.repository.PriorRepaymentApplicationRepository;
import org.kofi.creditex.repository.ProlongationApplicationRepository;
import org.kofi.creditex.web.model.CreditApplicationForm;
import org.kofi.creditex.web.model.PriorApplicationForm;
import org.kofi.creditex.web.model.ProlongationApplicationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Service
public class ApplicationServiceImpl implements ApplicationService {
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Function<Boolean, String> acceptanceTransformation = new Function<Boolean, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Boolean applicationAcceptance) {
            String acceptance ="";
            if (applicationAcceptance==null)
                acceptance = "В рассмотрении";
            else {
                if (applicationAcceptance)
                    acceptance = "Принята";
                else {
                    acceptance = "Отклонена";
                }
            }
            return acceptance;
        }
    };
    Function<Application, CreditApplicationForm> transformFunction = new Function<Application, CreditApplicationForm>() {
        @Nullable
        @Override
        public CreditApplicationForm apply(@Nullable Application application) {
            String acceptance = "";
            String whoRejected = "";
            String whyRejected = "";
            assert application != null;
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
    Function<ProlongationApplication, ProlongationApplicationForm> prolongationApplicationTransform = new Function<ProlongationApplication, ProlongationApplicationForm>() {
        @Nullable
        @Override
        public ProlongationApplicationForm apply(@Nullable ProlongationApplication prolongationApplication) {
            assert prolongationApplication != null;
            return new ProlongationApplicationForm()
                    .setId(prolongationApplication.getId())
                    .setComment(prolongationApplication.getComment())
                    .setDuration(prolongationApplication.getDuration())
                    .setDate(df.format(prolongationApplication.getApplicationDate()))
                    .setCreditId(prolongationApplication.getCredit().getId())
                    .setAcceptance(acceptanceTransformation.apply(prolongationApplication.getAcceptance()));
        }
    };
    Function<PriorRepaymentApplication, PriorApplicationForm> priorApplicationTransform = new Function<PriorRepaymentApplication, PriorApplicationForm>() {
        @Nullable
        @Override
        public PriorApplicationForm apply(@Nullable PriorRepaymentApplication priorRepaymentApplication) {
            assert priorRepaymentApplication != null;
            return new PriorApplicationForm()
                    .setId(priorRepaymentApplication.getId())
                    .setDate(df.format(priorRepaymentApplication.getApplicationDate()))
                    .setComment(priorRepaymentApplication.getComment())
                    .setCreditId(priorRepaymentApplication.getCredit().getId())
                    .setAcceptance(acceptanceTransformation.apply(priorRepaymentApplication.getAcceptance()));
        }
    };
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PriorRepaymentApplicationRepository priorRepaymentApplicationRepository;
    @Autowired
    private ProlongationApplicationRepository prolongationApplicationRepository;
    @Override
    public List<Application> GetApplicationsByUsername(String username) {
        return applicationRepository.findByClient_Username(username);
    }
    @Override
    public List<CreditApplicationForm> GetCreditApplicationsInListByUsername(String username){
        return Lists.transform(GetApplicationsByUsername(username), transformFunction);
    }

    @Override
    public List<PriorRepaymentApplication> GetPriorRepaymentApplicationsByUsername(String username) {
        return priorRepaymentApplicationRepository.findByClient_Username(username);
    }

    @Override
    public List<PriorApplicationForm> GetPriorApplicationFormsByUsername(String username) {
        return Lists.transform(GetPriorRepaymentApplicationsByUsername(username), priorApplicationTransform);
    }

    @Override
    public List<ProlongationApplication> GetProlongationApplicationsByUsername(String username) {
        return prolongationApplicationRepository.findByClient_Username(username);
    }

    @Override
    public List<ProlongationApplicationForm> GetProlongationApplicationFormsByUsername(String username) {
        return Lists.transform(GetProlongationApplicationsByUsername(username), prolongationApplicationTransform);
    }
}
