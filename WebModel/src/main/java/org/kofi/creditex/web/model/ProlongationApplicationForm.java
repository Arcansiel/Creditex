package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ProlongationApplicationForm {
    private long id;
    private String date;
    private long duration;
    private String comment;
    private long creditId;
    private String acceptance;
}
