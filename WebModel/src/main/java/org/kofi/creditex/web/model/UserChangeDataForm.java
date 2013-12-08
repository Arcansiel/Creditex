package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserChangeDataForm {
    private int id;
    private String first;
    private String last;
    private String patronymic;
    private String passportSeries;
    private int passportNumber;
    private String workName;
    private String workPosition;
    private int workIncome;
}
