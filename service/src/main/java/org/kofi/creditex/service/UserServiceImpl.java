package org.kofi.creditex.service;

import lombok.extern.slf4j.Slf4j;
import org.kofi.creditex.model.Authority;
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
}
