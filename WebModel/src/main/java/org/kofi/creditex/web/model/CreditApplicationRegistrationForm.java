package org.kofi.creditex.web.model;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CreditApplicationRegistrationForm {
    @Min(0)
    private long duration;
    @Min(0)
    private long request;
    @Min(0)
    private long productId;
}
