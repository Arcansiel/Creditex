package org.kofi.creditex.model;

import lombok.Data;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Data
@Entity
public class DayReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private LocalDate dayDate;
    private long currentBankMoney;
    private long clients;
    private long accountManagers;
    private long securityManagers;
    private long operationManagers;
    private long committeeManagers;
    private long operations;
    private long credits;
    private long runningCredits;
    private long expiredCredits;
    private long unreturnedCredits;
    private long creditApplications;
    private long priorRepaymentApplications;
    private long prolongationApplications;
    private long products;
}
