package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UserRegistrationForm{
    @Size(min = 8, max = 46)
    @Pattern(regexp = "\\w+")
    private String username;
    @Size(min = 8, max = 46)
    @Pattern(regexp = "\\w+")
    private String password;
    @Size(min = 8, max = 46)
    @Pattern(regexp = "\\w+")
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
    private String changePassword;
    private String changeRepeatPassword;
}