package org.kofi.creditex.service;

import org.kofi.creditex.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User GetUserByUsername(String username);
}
