package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PriorApplicationForm {
    private int id;
    private String comment;
    private int creditId;
    private String acceptance;
    private String date;
}
