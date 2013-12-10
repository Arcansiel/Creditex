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
    public Application GetApplication(int application_id) {
        return applicationRepository.findOne(application_id);
    }

    @Override
    public List<Vote> GetApplicationVotes(int application_id) {
        List<Vote> list = new ArrayList<Vote>();
        for(Vote v:voteRepository.findAll(
                QVote.vote.application.id.eq(application_id)
        )){
            list.add(v);
        }
        return list;
    }

    public List<Application> GetVotingApplications(boolean voting){
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.securityAcceptance.isTrue()
                        .and(QApplication.application.votingClosed.ne(voting))
                        .and(QApplication.application.request.goe(QApplication.application.product.minCommittee))
                , QApplication.application.applicationDate.desc()
        )){
            list.add(a);
        }
        return list;
    }

    private Vote GetVote(int committee_id, int application_id){
        return voteRepository.findOne(
                QVote.vote.application.id.eq(application_id).and(QVote.vote.manager.id.eq(committee_id))
        );
    }

    @Override
    public int Vote(String committee_name, int application_id, boolean acceptance, String comment) {
        Application a = applicationRepository.findOne(application_id);
        if(a != null){
            if(a.isVotingClosed()){
                return -1;
            }
        }else{
            return -2;
        }
        User u = userService.GetUserByUsername(committee_name);
        if(u == null){
            return -3;
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

    @Override
    public List<Credit> GetClientCredits(int client_id) {
        List<Credit> list = new ArrayList<Credit>();
        for(Credit c:creditRepository.findAll(
                QCredit.credit.user.id.eq(client_id),
                QCredit.credit.creditStart.desc()
        )){
            list.add(c);
        }
        return list;
    }

    public List<Application> GetCommitteeVotingCheckedApplications(int committee_id, boolean voting, boolean checked){
        List<Application> list = new ArrayList<Application>();
        for(Application a:applicationRepository.findAll(
                QApplication.application.securityAcceptance.isTrue()
                        .and(QApplication.application.votingClosed.ne(voting))
                        .and(QApplication.application.request.goe(QApplication.application.product.minCommittee))
                , QApplication.application.applicationDate.desc()
        )){
            if((voteRepository.count(QVote.vote.application.eq(a).and(QVote.vote.manager.id.eq(committee_id))) > 0) == checked){
                list.add(a);
            }
        }
        return list;
    }


}
