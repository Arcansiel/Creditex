package org.kofi.creditex.service;

import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.web.model.UserRegistrationForm;

import javax.annotation.PostConstruct;
import java.util.List;

public interface UserService {
    User GetUserByUsername(String username);
    User GetSecurityByUsername(String security_username);
    User GetOperatorByUsername(String operator_username);
    User GetCommitteeByUsername(String committee_username);
    User GetDepartmentHeadByUsername(String head_username);
    User GetUserByUserDataValues(UserData data);
    User GetClientByUserDataValues(UserData data);
    String GetHashedPassword(String unHashed);
    Authority GetAuthorityByAuthorityName(String authority);
    void RegisterUserByForm(UserRegistrationForm form);
    void SaveUser(User user);
    void ChangeUserDataByForm(UserData form);
    List<User> GetAllUsersByAuthority(String authority);
    User GetUserById(long user_id);
    User GetClientById(long client_id);
    void ChangeRegistrationDataByForm(UserRegistrationForm form);
    long GetUsersCountByAuthorityAndEnabled(String authority, boolean enabled);
    boolean SetUserEnabledById(long user_id, boolean enabled);

    @PostConstruct
    void Initialize();
}
