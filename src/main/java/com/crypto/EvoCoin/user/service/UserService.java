package com.crypto.EvoCoin.user.service;

import com.crypto.EvoCoin.user.dao.UserDao;
import com.crypto.EvoCoin.user.entity.User;
import com.crypto.EvoCoin.user.model.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public void saveUser(UserRegistration pUser){
        try {
            User vSave = new User(pUser);
            this.userDao.save(vSave);
        } catch (Exception e) {
            log.error("Failed: saveUser", e);
            throw e;
        }
    }
}
