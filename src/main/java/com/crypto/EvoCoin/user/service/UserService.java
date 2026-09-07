package com.crypto.EvoCoin.user.service;

import com.crypto.EvoCoin.user.dao.UserDao;
import com.crypto.EvoCoin.user.entity.User;
import com.crypto.EvoCoin.user.model.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void saveUser(UserRegistration pUser){
        String passwordHash = passwordEncoder.encode(pUser.password());
        User vSave = new User(pUser, passwordHash);
        this.userDao.save(vSave);
    }

    public User findUserByUsername(String pUser){
        return userDao.findUserByUsername(pUser);
    }
}
