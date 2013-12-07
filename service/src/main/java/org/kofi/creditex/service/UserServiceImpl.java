package org.kofi.creditex.service;

import org.kofi.creditex.model.User;
import org.kofi.creditex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 06.12.13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User GetUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
