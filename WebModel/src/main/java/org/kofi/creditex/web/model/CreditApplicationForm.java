package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreditApplicationForm {
    private int id;
    private String productName;
    private int productId;
    private int requestedMoney;
    private int duration;
    private String acceptance;
    private String applicationDate;
    private String whoRejected;
    private String whyRejected;
}
