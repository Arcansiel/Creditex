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
public class CommitteeServiceImpl implements CommitteeService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    UserService userService;

    @Override
    public Application GetApplication(long application_id) {
        return applicationRepository.findOne(application_id);
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
    public List<Application> GetApplicationsForVoting() {
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.securityAcceptance.eq(Acceptance.Accepted)
                        .and(QApplication.application.acceptance.eq(Acceptance.InProcess))
                        .and(QApplication.application.votingClosed.isFalse())
                        .and(QApplication.application.request.goe(QApplication.application.product.minCommittee))
                , QApplication.application.applicationDate.asc()
        )){
            list.add(a);
        }
        return list;
    }

    @Override
    public List<Application> GetVotingClosedApplications() {
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.securityAcceptance.eq(Acceptance.Accepted)
                        //.and(QApplication.application.acceptance.ne(Acceptance.InProcess))
                        .and(QApplication.application.request.goe(QApplication.application.product.minCommittee))
                        .and(QApplication.application.votingClosed.isTrue())
                , QApplication.application.applicationDate.desc()
        )){
            list.add(a);
        }
        return list;
    }

    private Vote GetVote(long committee_id, long application_id){
        return voteRepository.findOne(
                QVote.vote.application.id.eq(application_id).and(QVote.vote.manager.id.eq(committee_id))
        );
    }

    @Override
    public int Vote(String committee_name, long application_id, boolean acceptance, String comment) {
        User u = userService.GetCommitteeByUsername(committee_name);
        if(u == null){
            return -1;//no committee manager
        }
        Application a = applicationRepository.findOne(application_id);
        if(a != null){
            if(a.isVotingClosed()){
                return -4;//voting closed
            }
        }else{
            return -2;//no application
        }
        if(!a.getSecurityAcceptance().equals(Acceptance.Accepted)
                || !a.getAcceptance().equals(Acceptance.InProcess)
                || !a.getCommitteeAcceptance().equals(Acceptance.InProcess)
                ){
            return -3;//not in process
        }
        Vote v = GetVote(u.getId(), application_id);
        if(v == null){
            v = new Vote();
            v.setAcceptance(acceptance);
            v.setApplication(a);
            v.setManager(u);
            v.setComment(comment);
            voteRepository.save(v);
            if(acceptance){
                a.setVoteAcceptance(a.getVoteAcceptance()+1);
            }else{
                a.setVoteRejection(a.getVoteRejection()+1);
            }
            AfterVoteProcessing(a);
            applicationRepository.save(a);
        }else{
            boolean vacceptance = v.isAcceptance();
            v.setComment(comment);
            if(vacceptance != acceptance){
                v.setAcceptance(acceptance);
                if(acceptance){
                    a.setVoteRejection(a.getVoteRejection()-1);
                    a.setVoteAcceptance(a.getVoteAcceptance()+1);
                }else{
                    a.setVoteAcceptance(a.getVoteAcceptance()-1);
                    a.setVoteRejection(a.getVoteRejection()+1);
                }
            }
            voteRepository.save(v);
            applicationRepository.save(a);
        }

        return 0;
    }



    private long GetVotesLimit(){
        //TODO
        return userService.GetUsersCountByAuthorityAndEnabled("ROLE_COMMITTEE_MANAGER",true);
    }

    private void AfterVoteProcessing(Application application){
        long votes_limit = GetVotesLimit();
        long votes_count = application.getVoteAcceptance() + application.getVoteRejection();
        if(votes_count >= votes_limit){
            //close voting
            application.setVotingClosed(true);
            //set acceptance
            if(application.getVoteAcceptance() >= application.getVoteRejection()){
                application.setCommitteeAcceptance(Acceptance.Accepted);
            }else{
                application.setCommitteeAcceptance(Acceptance.Rejected);
                application.setAcceptance(Acceptance.Rejected);
                application.setProcessed(true);//обработка заявки завершена, заявка отклонена
            }
        }
    }

    /*public List<Application> GetCommitteeVotingCheckedApplications(long committee_id, boolean voting, boolean checked){
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.securityAcceptance.eq(Acceptance.Accepted)
                        .and(QApplication.application.votingClosed.ne(voting))
                        .and(QApplication.application.request.goe(QApplication.application.product.minCommittee))
                , QApplication.application.applicationDate.desc()
        )){
            if((voteRepository.count(QVote.vote.application.eq(a).and(QVote.vote.manager.id.eq(committee_id))) > 0) == checked){
                list.add(a);
            }
        }
        return list;
    }*/


}
