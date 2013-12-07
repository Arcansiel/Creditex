package org.kofi.creditex.service;

import org.kofi.creditex.model.Authority;
import org.kofi.creditex.model.User;

import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User GetUserByUsername(String username);
    User GetUserByUserDataValues(String first, String last, String patronymic, String series, int number);
    String GetHashedPassword(String unHashed, String salt) throws NoSuchAlgorithmException;
    Authority GetAuthorityByAuthorityName(String authority);
    void SaveUser(User user);
}
