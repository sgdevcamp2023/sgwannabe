package authserver.service;

import authserver.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
//@Transactional
public class RedisService {

    @Value("${jwt.refresh-expiration}") //30초
    private long jwtRefreshExpiration;

    private final RedisTemplate<String, String> redisTemplate;

    private final AuthRepository authRepository;

    // key: refreshToken, value: email -> 시간복잡도 (O(1))
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
