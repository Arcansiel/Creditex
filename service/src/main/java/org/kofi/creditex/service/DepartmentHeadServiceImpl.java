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
        Acceptance eApp;
        if (approved)
            eApp = Acceptance.Accepted;
        else eApp = Acceptance.Rejected;
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.request.goe(QApplication.application.product.minCommittee)//нужно одобрение
                        .and(QApplication.application.securityAcceptance.eq(Acceptance.Accepted))//одобрена службой безопасности
                        .and(QApplication.application.committeeAcceptance.eq(eApp))//одобрение комитета
                        .and(QApplication.application.headAcceptance.isNull()),//не проверена главой отдела
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
        Acceptance eApp;
        if (acceptance)
            eApp = Acceptance.Accepted;
        else eApp = Acceptance.Rejected;
        Application application = applicationRepository.findOne(application_id);
        if(application == null){ return -1; }//no app
        User head = userService.GetUserByUsername(head_username);
        if(head == null){ return -2; }//no user
        application.setHeadAcceptance(eApp);//d/p/i/a/i/n
        application.setAcceptance(eApp);///////u/l/c/t/o/
        application.setHeadComment(comment);
        application.setHead(head);
        applicationRepository.save(application);
        return 0;
    }

    @Override
    public List<ProlongationApplication> GetUncheckedProlongations() {
        List<ProlongationApplication> list = new ArrayList<ProlongationApplication>();
        for(ProlongationApplication p:prolongationRepository.findAll(
              QProlongationApplication.prolongationApplication.acceptance.isNull(),
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
        Acceptance eApp;
        if (acceptance)
            eApp = Acceptance.Accepted;
        else eApp = Acceptance.Rejected;
        ProlongationApplication p = prolongationRepository.findOne(prolongation_id);
        if(p == null){ return -1; }//no app
        p.setAcceptance(eApp);
        prolongationRepository.save(p);

        //TODO Credit prolongation logic

        return 0;
    }
}
