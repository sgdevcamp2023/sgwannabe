package userserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import userserver.repository.UserRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    // key: email, vale: authcode
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
        redisTemplate.opsForValue().set(key, value, duration); // ttl 설정
    }

    public Boolean isExistKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
