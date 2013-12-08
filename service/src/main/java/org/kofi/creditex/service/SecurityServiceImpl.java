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

    public boolean ConfirmApplication(int security_id, int id, boolean confirmation, String comment){
        //TODO

        return true;
    }
}
