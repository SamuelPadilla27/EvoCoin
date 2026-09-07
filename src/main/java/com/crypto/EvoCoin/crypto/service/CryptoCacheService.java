package com.crypto.EvoCoin.crypto.service;

import com.crypto.EvoCoin.common.util.CryptoPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
public class CryptoCacheService {

    private static final int MAX_SIZE = 100;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public CryptoCacheService(
            @Qualifier("evocoinRedisTemplate") RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Cache initialization with 100 historic data.
     */
    public void initialize(String key, List<CryptoPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            return;
        }

        List<String> values = prices.stream()
                .map(this::toJson)
                .toList();

        redisTemplate.delete(key);

        redisTemplate.opsForList()
                .rightPushAll(key, values);

        redisTemplate.opsForList()
                .trim(key, 0, MAX_SIZE - 1);
    }

    /**
     * Update the first item in the list; if it has the same timestamp, update the price; otherwise, insert the new item
     * after the first one and delete the last one.
     * @param key Crypto key
     * @param price Crypto price
     */
    public void update(String key, CryptoPrice price) {

        String json = toJson(price);

        CryptoPrice current = getFirst(key);

        if (current == null) {

            redisTemplate.opsForList()
                    .leftPush(key, json);

            return;
        }

        if (current.timestamp() == (price.timestamp())) {

            redisTemplate.opsForList()
                    .set(key, 0, json);

        } else if(current.timestamp() < price.timestamp()) {
            redisTemplate.opsForList()
                    .leftPush(key, json);

            redisTemplate.opsForList()
                    .trim(key, 0, MAX_SIZE - 1);
        }
    }

    private CryptoPrice getFirst(String key) {

        String json = redisTemplate.opsForList()
                .index(key, 0);

        if (json == null) {
            return null;
        }

        return fromJson(json);
    }

    private String toJson(CryptoPrice pCryptoPrice){
        try {
            return objectMapper.writeValueAsString(pCryptoPrice);
        } catch (JacksonException e) {
            throw new RuntimeException("Error serializing CryptoPrice",e);
        }
    }

    private CryptoPrice fromJson(String pJson){
        try {
            return objectMapper.readValue(pJson, CryptoPrice.class);
        } catch (JacksonException e) {
            throw new RuntimeException("Error deserializing CryptoPrice" ,e);
        }
    }

}
