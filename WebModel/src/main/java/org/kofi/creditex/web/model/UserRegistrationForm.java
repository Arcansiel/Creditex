package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UserRegistrationForm{
    private String username;
    private String password;
    private String repeatPassword;
    private String first;
    private String last;
    private String patronymic;
    @Size(max = 2, min = 2)
    private String series;
    private int number;
    private String workName;
    private String workPosition;
    private long workIncome;
    private String role;
}