package com.crypto.EvoCoin.user.service;

import com.crypto.EvoCoin.user.dao.UserDao;
import com.crypto.EvoCoin.user.entity.User;
import com.crypto.EvoCoin.user.model.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    public void saveUser(UserRegistration pUser){
        try {
            String passwordHash = passwordEncoder.encode(pUser.password());
            User vSave = new User(pUser, passwordHash);
            this.userDao.save(vSave);
        } catch (Exception e) {
            log.error("Failed: saveUser", e);
            throw e;
        }
    }

    public User findUserByUsername(String pUser){
        try {
            return userDao.findUserByUsername(pUser);
        } catch (Exception e) {
            log.error("Failed: findUserByUsername", e);
            throw e;
        }
    }
}
