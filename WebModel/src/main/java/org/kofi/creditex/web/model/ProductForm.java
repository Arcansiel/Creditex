package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductForm {
    private int id;
    private String name;
    private String type;
    private int percent;
    private int minCommittee;
    private int minMoney;
    private int maxMoney;
    private int minDuration;
    private int maxDuration;
    private int finePercent;
    private String priorRepayment;
    private int priorRepaymentPercent;
}
