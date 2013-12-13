package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductForm {
    private long id;
    private String name;
    private boolean active;
    private String type;
    private float percent;
    private long minCommittee;
    private long minMoney;
    private long maxMoney;
    private long minDuration;
    private long maxDuration;
    private float finePercent;
    private String priorRepayment;
    private float priorRepaymentPercent;
}
