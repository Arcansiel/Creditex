package org.kofi.creditex.service;

import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.web.model.UserChangeDataForm;
import org.kofi.creditex.web.model.UserRegistrationForm;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    User GetUserByUsername(String username);
    User GetUserByUserDataValues(String first, String last, String patronymic, String series, long number);
    String GetHashedPassword(String unHashed);
    Authority GetAuthorityByAuthorityName(String authority);
    void RegisterUserByForm(UserRegistrationForm form);
    void SaveUser(User user);
    void ChangeUserDataByForm(UserChangeDataForm form);
}
