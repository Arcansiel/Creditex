package org.kofi.creditex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
public class DayReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate dayDate;
    private long currentBankMoney;
    private long overallCredits;
    private long overallClosedCredits;
    private long overallExpiredCredits;
    private long overallUnreturnedCredits;
    private long overallCreditApplications;
    private long overallPriorRepaymentApplications;
    private long overallProlongationApplications;
    private long clients;
    private long accountManagers;
    private long securityManagers;
    private long operationManagers;
    private long committeeManagers;
    private long operations;
    private long income;
    private long outcome;
    private long credits;
    private long expiredCredits;
    private long unreturnedCredits;
    private long creditApplications;
    private long priorRepaymentApplications;
    private long prolongationApplications;
    private long products;
    @Transient
    public long getOverallRunningCredits(){
        return overallCredits - overallClosedCredits;
    }
    @Transient
    public String getDayDateString(){
        return dayDate.toString("dd.MM.yyyy");
    }
}
