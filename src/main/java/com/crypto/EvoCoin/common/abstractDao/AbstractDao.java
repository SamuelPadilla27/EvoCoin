package com.crypto.EvoCoin.common.abstractDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractDao<T, ID> {

    private Class<T> clazz;
    @PersistenceContext
    protected EntityManager entityManager;

    public void setClazz(final Class<T> clazz){
        this.clazz = clazz;
    }

//    public EntityManager getEntityManager() {
//        return entityManager;
//    }


    public void save(T entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            log.error("Failed: Abstract Dao: save", e);
            throw new RuntimeException("Failed: Abstract Dao: save", e);
        }
    }

    public T findById(Class<T> clazz, ID id) {

        try {
            return entityManager.find(clazz, id);
        } catch (Exception e) {
            log.error("Failed: Abstract Dao: findById", e);
            throw new RuntimeException("Failed: Abstract Dao: findById", e);
        }
    }
}
