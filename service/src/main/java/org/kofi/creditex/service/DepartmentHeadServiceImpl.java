package org.kofi.creditex.service;

import org.kofi.creditex.model.*;

import java.util.List;
import java.util.ArrayList;
import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DepartmentHeadServiceImpl implements DepartmentHeadService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProlongationApplicationRepository prolongationRepository;

    @Autowired
    PriorRepaymentApplicationRepository priorRepaymentApplicationRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CreditService creditService;

    @Override
    public List<Application> GetCommitteeApprovedUncheckedApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.request.goe(QApplication.application.product.minCommittee)//нужно одобрение
                        .and(QApplication.application.securityAcceptance.eq(Acceptance.Accepted))//одобрена службой безопасности
                        .and(QApplication.application.committeeAcceptance.eq(Acceptance.Accepted))//одобрена комитетом
                        .and(QApplication.application.acceptance.eq(Acceptance.InProcess))
                        .and(QApplication.application.headAcceptance.eq(Acceptance.InProcess))//не проверена главой отдела
                ,QApplication.application.applicationDate.asc()
        )){
            list.add(a);
        }
        return list;
    }

    @Override
    public List<Application> GetCommitteeRejectedApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.request.goe(QApplication.application.product.minCommittee)//нужно одобрение
                        .and(QApplication.application.securityAcceptance.eq(Acceptance.Accepted))//одобрена службой безопасности
                        .and(QApplication.application.committeeAcceptance.eq(Acceptance.Rejected))//отклонена комитетом
                ,QApplication.application.applicationDate.desc()
        )){
            list.add(a);
        }
        return list;
    }

    @Override
    public List<Application> GetCommitteeVoteApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.request.goe(QApplication.application.product.minCommittee)//нужно одобрение
                .and(QApplication.application.securityAcceptance.eq(Acceptance.Accepted))//одобрена службой безопасности
                .and(QApplication.application.acceptance.eq(Acceptance.InProcess))
                .and(QApplication.application.votingClosed.isFalse()),//голосование не завершено
                QApplication.application.applicationDate.asc()
        )){
            list.add(a);
        }
        return list;
    }

    @Override
    public Application GetApplicationById(long id) {
        return applicationRepository.findOne(id);
    }

    @Override
    public List<Vote> GetApplicationVotes(long application_id) {
        List<Vote> list = new ArrayList<Vote>();
        for(Vote v:voteRepository.findAll(
                QVote.vote.application.id.eq(application_id)
        )){
            list.add(v);
        }
        return list;
    }

    @Override
    public int SetApplicationHeadApproved(long application_id, String head_username, boolean acceptance, String comment) {
        Acceptance acceptance_value;
        if (acceptance){
            acceptance_value = Acceptance.Accepted;
        }else{
            acceptance_value = Acceptance.Rejected;
        }
        Application application = applicationRepository.findOne(application_id);
        if(application == null){ return -1; }//no app
        User head = userService.GetUserByUsername(head_username);
        if(head == null){ return -2; }//no user
        if(!application.getAcceptance().equals(Acceptance.InProcess)){
            return -3;//not in process
        }
        if(!application.isVotingClosed()){
            return -4;//voting is not closed yet
        }
        application.setHeadAcceptance(acceptance_value);
        application.setAcceptance(acceptance_value);
        if(!acceptance){
            application.setProcessed(true);//обработка заявки завершена, заявка отклонена
        }
        application.setHeadComment(comment);
        application.setHead(head);
        applicationRepository.save(application);
        return 0;
    }

    @Override
    public List<ProlongationApplication> GetUncheckedProlongations() {
        List<ProlongationApplication> list = new ArrayList<ProlongationApplication>();
        for(ProlongationApplication p:prolongationRepository.findAll(
              QProlongationApplication.prolongationApplication.acceptance.eq(Acceptance.InProcess),
                QProlongationApplication.prolongationApplication.applicationDate.asc()
        )){
             list.add(p);
        }
        return list;
    }

    @Override
    public ProlongationApplication GetProlongation(long id) {
        return prolongationRepository.findOne(id);
    }

    @Override
    public int SetProlongationApproved(long prolongation_id, boolean acceptance) {
        Acceptance acceptance_value;
        if (acceptance){
            acceptance_value = Acceptance.Accepted;
        }else{
            acceptance_value = Acceptance.Rejected;
        }
        ProlongationApplication p = prolongationRepository.findOne(prolongation_id);
        if(p == null){ return -1; }//no app
        if(!p.getAcceptance().equals(Acceptance.InProcess)){
            return -2;//not in process
        }
        p.setAcceptance(acceptance_value);
        if(!acceptance){
            p.setProcessed(true);//обработка заявки завершена, заявка отклонена
        }
        prolongationRepository.save(p);

        return 0;
    }

    @Override
    public List<PriorRepaymentApplication> GetUncheckedPriors() {
        List<PriorRepaymentApplication> list = new ArrayList<PriorRepaymentApplication>();
        for(PriorRepaymentApplication p:priorRepaymentApplicationRepository.findAll(
                QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.InProcess),
                QPriorRepaymentApplication.priorRepaymentApplication.applicationDate.asc()
        )){
            list.add(p);
        }
        return list;
    }

    @Override
    public PriorRepaymentApplication GetPrior(long id) {
        return priorRepaymentApplicationRepository.findOne(id);
    }

    @Override
    public int SetPriorApproved(long prior_id, boolean acceptance) {
        Acceptance acceptance_value;
        if (acceptance){
            acceptance_value = Acceptance.Accepted;
        }else{
            acceptance_value = Acceptance.Rejected;
        }
        PriorRepaymentApplication p = priorRepaymentApplicationRepository.findOne(prior_id);
        if(p == null){ return -1; }//no app
        if(!p.getAcceptance().equals(Acceptance.InProcess)){
            return -2;//not in process
        }
        p.setAcceptance(acceptance_value);
        if(!acceptance){
            p.setProcessed(true);//обработка заявки завершена, заявка отклонена
        }
        priorRepaymentApplicationRepository.save(p);

        return 0;
    }
}
