package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientSearchForm {
    private String first;
    private String last;
    private String patronymic;
    private String series;
    private int number;
}
