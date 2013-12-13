package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductForm {
    private long id;
    private String name;
    private String type;
    private long percent;
    private long minCommittee;
    private long minMoney;
    private long maxMoney;
    private long minDuration;
    private long maxDuration;
    private long finePercent;
    private String priorRepayment;
    private long priorRepaymentPercent;
}
