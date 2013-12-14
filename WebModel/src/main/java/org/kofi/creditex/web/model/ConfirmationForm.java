package org.kofi.creditex.web.model;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ConfirmationForm {
    @Min(0)
    private long id;
    private boolean acceptance;
    @Size(max = 4000)
    private String comment;
}
