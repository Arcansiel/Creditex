package org.kofi.creditex.web;

import lombok.Data;

@Data
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
    private int workIncome;
    private String role;
}