package org.kofi.creditex.repository;

import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.QCredit;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

class CreditRepositoryImpl implements CreditRepositoryExt {
    @Autowired
    private CreditRepository creditRepository;
    @Override
    public List<Credit> findCreditsToClose(Date currentDate) {
        QCredit credit = QCredit.credit;
        Predicate query = credit
                .creditEnd.loe(currentDate)
                .and(credit.currentMainDebt.eq((long) 0))
                .and(credit.currentPercentDebt.eq((long) 0))
                .and(credit.mainFine.eq((long) 0))
                .and(credit.percentFine.eq((long) 0));
        return Lists.newArrayList(creditRepository.findAll(query));
    }
}
