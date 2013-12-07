package org.kofi.creditex.service;

import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;
import org.kofi.creditex.repository.AuthoritiesRepository;
import org.kofi.creditex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Override
    public User GetUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User GetUserByUserDataValues(String first, String last, String patronymic, String series, int number) {
        return userRepository.findByUserData_FirstAndUserData_LastAndUserData_PatronymicAndUserData_PassportSeriesAndUserData_PassportNumber(first, last, patronymic, series, number);
    }

    @Override
    public String GetHashedPassword(String unHashed, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt.getBytes());
        byte byteData[] = digest.digest(unHashed.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Authority GetAuthorityByAuthorityName(String authority) {
        return authoritiesRepository.findByAuthority(authority);
    }

    @Override
    public void SaveUser(User user) {
        userRepository.save(user);
    }
}
