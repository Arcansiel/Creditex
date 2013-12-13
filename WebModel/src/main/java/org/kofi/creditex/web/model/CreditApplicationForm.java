package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreditApplicationForm {
    private long id;
    private String productName;
    private long productId;
    private long requestedMoney;
    private long duration;
    private String acceptance;
    private String applicationDate;
    private String whoRejected;
    private String whyRejected;
}
