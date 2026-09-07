package com.crypto.EvoCoin.crypto.dao;

import com.crypto.EvoCoin.common.abstractDao.AbstractDao;
import com.crypto.EvoCoin.crypto.entity.Crypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CryptoDao extends AbstractDao<Crypto, Long> {

    public CryptoDao() {
        this.setClazz(Crypto.class);
    }

    public List<Crypto> findAll(){
        try {
            return entityManager.createQuery("SELECT crypto FROM Crypto as crypto"
            , Crypto.class).getResultStream().toList();
        } catch (Exception e) {
            log.error("Failed: findAll", e);
            throw new RuntimeException("Failed: findAll", e);
        }
    }
}
