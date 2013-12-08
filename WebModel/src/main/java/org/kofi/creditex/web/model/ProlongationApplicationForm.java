package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ProlongationApplicationForm {
    private int id;
    private String date;
    private int duration;
    private String comment;
    private int creditId;
    private String acceptance;
}
