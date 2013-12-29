package org.kofi.creditex.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Data
@Accessors(chain = true)
public class UserRegistrationForm{
    @Size(min = 8, max = 46)
    @Pattern(regexp = "^\\w+$")
    private String username;
    @Size(min = 8, max = 46)
    @Pattern(regexp = "^\\w+$")
    private String password;
    @Size(min = 8, max = 46)
    @Pattern(regexp = "^\\w+$")
    private String repeatPassword;
    @Size(max = 46)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Alpha word please")
    private String first;
    @Size(max = 46)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Alpha word please")
    private String last;
    @Size(max = 46)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Alpha word please")
    private String patronymic;
    @Size(max = 2, min = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "Passport combination")
    private String series;
    @Min(1)
    @Max(9999999)
    private int number;
    @Size(max = 46)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Alpha word please")
    private String workName;
    @Size(max = 46)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Alpha word please")
    private String workPosition;
    @Min(0)
    private long workIncome;
    private String role;
    private String changePassword;
    private String changeRepeatPassword;
    @AssertTrue(message = "Passwords don't match")
    private boolean matchPasswords(){
        return password.equals(repeatPassword) & changePassword.equals(changeRepeatPassword);
    }
}