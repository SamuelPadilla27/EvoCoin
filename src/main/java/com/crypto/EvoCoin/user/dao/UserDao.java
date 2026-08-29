package com.crypto.EvoCoin.user.dao;

import com.crypto.EvoCoin.common.abstractDao.AbstractDao;
import com.crypto.EvoCoin.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserDao extends AbstractDao<User, Long> {

    public UserDao() {
        this.setClazz(User.class);
    }

    public User findUserByUsername(String pUsername){

        try {
            return getCurrentSession().createQuery(
                    "SELECT user FROM User AS user WHERE user.username = :pUsername "
                    , User.class)
                    .setParameter("pUsername", pUsername)
                    .uniqueResult();
        } catch (RuntimeException e) {
            log.error("Failed: findUserByUsername", e);
            throw new RuntimeException(e);
        }
    }

}
