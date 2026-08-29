package com.crypto.EvoCoin.user.dao;

import com.crypto.EvoCoin.common.abstractDao.AbstractDao;
import com.crypto.EvoCoin.user.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDao extends AbstractDao<User, Long> {

    public UserDao() {
        this.setClazz(User.class);
    }

}
