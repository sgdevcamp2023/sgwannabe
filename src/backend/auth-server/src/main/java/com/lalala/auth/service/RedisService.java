package com.lalala.auth.service;

import com.lalala.auth.repository.AuthRepository;
import java.time.Duration;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Value("${jwt.refresh-expiration}") // 30초
    private long jwtRefreshExpiration;

    private final RedisTemplate<String, String> redisTemplate;


    // key: id, value: refreshToken
    public String getRedisTemplateValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRedisTemplateValue(String key) {
        redisTemplate.delete(key);
    }

    public void setRedisTemplate(String key, String value, Duration duration) {
        if (getRedisTemplateValue(key) != null) {
            deleteRedisTemplateValue(key);
        }
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public Boolean isExistKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
