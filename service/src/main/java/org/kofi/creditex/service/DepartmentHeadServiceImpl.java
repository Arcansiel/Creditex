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
    VoteRepository voteRepository;

    @Override
    public List<Application> GetCommitteeApprovedUncheckedApplications(boolean approved) {
        Acceptance acceptance;
        if (approved){
            acceptance = Acceptance.Accepted;
        }else{
            acceptance = Acceptance.Rejected;
        }
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.request.goe(QApplication.application.product.minCommittee)//нужно одобрение
                        .and(QApplication.application.securityAcceptance.eq(Acceptance.Accepted))//одобрена службой безопасности
                        .and(QApplication.application.committeeAcceptance.eq(acceptance))//одобрение комитета
                        .and(QApplication.application.headAcceptance.eq(Acceptance.InProcess)),//не проверена главой отдела
                QApplication.application.applicationDate.asc()
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
            return -3;//not in progress
        }
        if(!application.isVotingClosed()){
            return -4;//voting is not closed yet
        }
        application.setHeadAcceptance(acceptance_value);
        application.setAcceptance(acceptance_value);
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
            return -2;//not in progress
        }
        p.setAcceptance(acceptance_value);
        prolongationRepository.save(p);

        //TODO Credit prolongation logic ?

        return 0;
    }
}
