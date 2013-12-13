package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentTableForm {
    private long id;
    private long number;
    private long payment;
    private String start;
    private String end;
    private String expired;
    private String closed;
}
