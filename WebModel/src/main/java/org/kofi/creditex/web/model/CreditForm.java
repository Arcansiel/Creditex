package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreditForm {
    private long id;
    private String start;
    private long duration;
    private long currentMainDebt;
    private long fine;
    private long currentMoney;
    private long originalMainDebt;
    private long productId;
    private String productName;
    private List<PaymentTableForm> payments;
}
