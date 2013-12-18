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

    @Override
    public User GetUserByUserDataValues(UserData data) {
        return userRepository.findByUserData_FirstAndUserData_LastAndUserData_PatronymicAndUserData_PassportSeriesAndUserData_PassportNumber(data.getFirst(), data.getLast(), data.getPatronymic(), data.getPassportSeries(), data.getPassportNumber());
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
}
