package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentTableForm {
    private int id;
    private int number;
    private int payment;
    private String start;
    private String end;
    private String expired;
    private String closed;
}
