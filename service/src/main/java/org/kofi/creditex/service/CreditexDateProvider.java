package org.kofi.creditex.service;


import lombok.Data;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Data
@Service
public class CreditexDateProvider {
    private LocalDate currentDate = LocalDate.now();

    public Date getCurrentSqlDate(){
        return new java.sql.Date(currentDate.toDate().getTime());
    }
}
