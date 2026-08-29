package com.crypto.EvoCoin.common.abstractDao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractDao<T, ID> {

    private Class<T> clazz;
    @Autowired
    private SessionFactory sessionFactory;

    public void setClazz(final Class<T> clazz){
        this.clazz = clazz;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public void save(T pSave){
        try {
            getCurrentSession().persist(pSave);
        } catch (RuntimeException e) {
            log.error("Failed: AbstractDao save", e);
            throw new RuntimeException(e);
        }
    }

    public T findById(ID id){
        return getCurrentSession().find(clazz, id);
    }
}
