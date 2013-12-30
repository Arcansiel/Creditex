package org.kofi.creditex.service;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.QUser;
import org.kofi.creditex.model.User;
import org.kofi.creditex.model.UserData;
import org.kofi.creditex.repository.AuthoritiesRepository;
import org.kofi.creditex.repository.UserDataRepository;
import org.kofi.creditex.repository.UserRepository;
import org.kofi.creditex.web.model.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public User GetUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private User GetUserByUsernameAndAuthority(String username, String authority) {
        User user = userRepository.findByUsername(username);
        if(user != null && user.getAuthority().getAuthority().equals(authority)){
            return user;
        }else{
            return null;
        }
    }

    @Override
    public User GetSecurityByUsername(String security_username) {
        return GetUserByUsernameAndAuthority(security_username,"ROLE_SECURITY_MANAGER");
    }

    @Override
    public User GetOperatorByUsername(String operator_username) {
        return GetUserByUsernameAndAuthority(operator_username,"ROLE_OPERATION_MANAGER");
    }

    @Override
    public User GetCommitteeByUsername(String committee_username) {
        return GetUserByUsernameAndAuthority(committee_username,"ROLE_COMMITTEE_MANAGER");
    }

    @Override
    public User GetDepartmentHeadByUsername(String head_username) {
        return GetUserByUsernameAndAuthority(head_username,"ROLE_DEPARTMENT_HEAD");
    }


    @Override
    public User GetUserByUserDataValues(UserData data) {
        log.warn("User data to search: {}", data);
        return userRepository.findByUserData_FirstAndUserData_LastAndUserData_PatronymicAndUserData_PassportSeriesAndUserData_PassportNumber(data.getFirst(), data.getLast(), data.getPatronymic(), data.getPassportSeries(), data.getPassportNumber());
    }

    private User GetUserByUserDataValuesAndAuthority(UserData data, String authority){
        log.warn("User data to search: {}", data);
        User user =
                userRepository.findByUserData_FirstAndUserData_LastAndUserData_PatronymicAndUserData_PassportSeriesAndUserData_PassportNumber(data.getFirst(), data.getLast(), data.getPatronymic(), data.getPassportSeries(), data.getPassportNumber());
        if(user != null && user.getAuthority().getAuthority().equals(authority)){
            return user;
        }else{
            return null;
        }
    }

    @Override
    public User GetClientByUserDataValues(UserData data) {
        return GetUserByUserDataValuesAndAuthority(data,"ROLE_CLIENT");
    }

    @Override
    public String GetHashedPassword(String unHashed) {
        String hashed = encoder.encode(unHashed);
        UserServiceImpl.log.warn("Generated hash: "+hashed);
        return hashed;
    }

    @Override
    public Authority GetAuthorityByAuthorityName(String authority) {
        return authoritiesRepository.findByAuthority(authority);
    }

    @Override
    public void RegisterUserByForm(UserRegistrationForm form) {
        UserData userData = new UserData();
        userData
                .setFirst(form.getFirst())
                .setLast(form.getLast())
                .setPatronymic(form.getPatronymic())
                .setPassportSeries(form.getSeries())
                .setPassportNumber(form.getNumber())
                .setWorkName(form.getWorkName())
                .setWorkPosition(form.getWorkPosition())
                .setWorkIncome(form.getWorkIncome());
        Authority authority = GetAuthorityByAuthorityName(form.getRole());
        User user = new User();
        user
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setCredentialsNonExpired(true)
                .setEnabled(true)
                .setUsername(form.getUsername())
                .setPassword(GetHashedPassword(form.getPassword()))
                .setUserData(userData)
                .setAuthority(authority);
        SaveUser(user);
    }

    @Override
    public void SaveUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void ChangeUserDataByForm(UserData form) {
        userDataRepository.save(form);
    }

    @Override
    public List<User> GetAllUsersByAuthority(String authority) {
        Authority a = authoritiesRepository.findByAuthority(authority);
        if(a == null){
            return null;
        }
        List<User> list = new ArrayList<User>();
        for(User user:userRepository.findAll(
            QUser.user.authority.eq(a)
        )){
            list.add(user);
        }
        return list;
    }

    @Override
    public User GetUserById(long user_id) {
        return userRepository.findOne(user_id);
    }


    private User GetUserByIdAndAuthority(long user_id, String authority) {
        User user = userRepository.findOne(user_id);
        if(user != null && user.getAuthority().getAuthority().equals(authority)){
            return user;
        }else{
            return null;
        }
    }

    @Override
    public User GetClientById(long client_id){
        return GetUserByIdAndAuthority(client_id,"ROLE_CLIENT");
    }

    @Override
    public void ChangeRegistrationDataByForm(UserRegistrationForm form) {
        User user = userRepository.findByUsername(form.getUsername());
        if (!form.getPassword().equals("")&&form.getPassword().equals(form.getRepeatPassword()))
            user.setPassword(encoder.encode(form.getPassword()));
        UserData data = user.getUserData();
        data.setFirst(form.getFirst())
                .setLast(form.getLast())
                .setPatronymic(form.getPatronymic())
                .setPassportSeries(form.getSeries())
                .setPassportNumber(form.getNumber())
                .setWorkName(form.getWorkName())
                .setWorkPosition(form.getWorkPosition())
                .setWorkIncome(form.getWorkIncome());
        userRepository.save(user);
    }

    @Override
    public long GetUsersCountByAuthorityAndEnabled(String authority, boolean enabled) {
        return userRepository.count(
            QUser.user.authority.authority.eq(authority)
                .and(QUser.user.enabled.eq(enabled))
        );
    }

    @Override
    public boolean SetUserEnabledById(long user_id, boolean enabled) {
        User user = userRepository.findOne(user_id);
        if(user == null){
            return false;
        }
        user.setEnabled(enabled);
        userRepository.save(user);
        return true;
    }

    @Override
    @PostConstruct
    public void Initialize(){
        if (authoritiesRepository.count() == 0){
            log.error("Authorities table is empty, filling with default values");
            List<Authority> authorities = new ArrayList<>(6);
            authorities.add(new Authority().setAuthority("ROLE_CLIENT"));
            authorities.add(new Authority().setAuthority("ROLE_ACCOUNT_MANAGER"));
            authorities.add(new Authority().setAuthority("ROLE_SECURITY_MANAGER"));
            authorities.add(new Authority().setAuthority("ROLE_COMMITTEE_MANAGER"));
            authorities.add(new Authority().setAuthority("ROLE_DEPARTMENT_HEAD"));
            authorities.add(new Authority().setAuthority("ROLE_OPERATION_MANAGER"));
            authoritiesRepository.save(authorities);
        }
        else if(authoritiesRepository.count() !=6){
            log.error("Authorities table was modified. Exiting.");
            System.exit(-1);
        }



    }
}
