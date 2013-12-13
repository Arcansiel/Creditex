package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PriorApplicationForm {
    private long id;
    private String comment;
    private long creditId;
    private String acceptance;
    private String date;
}
