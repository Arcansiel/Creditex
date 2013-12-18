package org.kofi.creditex.service;

import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.web.model.UserRegistrationForm;

import java.util.List;

public interface UserService {
    User GetUserByUsername(String username);
    User GetUserByUserDataValues(UserData data);
    String GetHashedPassword(String unHashed);
    Authority GetAuthorityByAuthorityName(String authority);
    void RegisterUserByForm(UserRegistrationForm form);
    void SaveUser(User user);
    void ChangeUserDataByForm(UserData form);
    List<User> GetAllUsersByAuthority(String authority);
    User GetUserById(long user_id);
    void ChangeRegistrationDataByForm(UserRegistrationForm form);
    long GetUsersCountByAuthorityAndEnabled(String authority, boolean enabled);
    boolean SetUserEnabledById(long user_id, boolean enabled);
}
