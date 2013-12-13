package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRegistrationForm{
    private String username;
    private String password;
    private String repeatPassword;
    private String first;
    private String last;
    private String patronymic;
    private String series;
    private int number;
    private String workName;
    private String workPosition;
    private long workIncome;
    private String role;
}