package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreditForm {
    private int id;
    private String start;
    private int duration;
    private int currentMainDebt;
    private int fine;
    private int currentMoney;
    private int originalMainDebt;
    private int productId;
    private String productName;
    private List<PaymentTableForm> payments;
}
