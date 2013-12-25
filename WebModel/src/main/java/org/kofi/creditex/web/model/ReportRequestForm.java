package org.kofi.creditex.web.model;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ReportRequestForm {
    @Min(1)
    private int period;
}
