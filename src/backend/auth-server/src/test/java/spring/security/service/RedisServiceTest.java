package spring.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.auth.service.RedisService;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    void getRedis() {
        //given
        String key = "token";
        String value = "email";

        long expiredTime = 30 * 1000; // 30초
        redisService.setRedisTemplate(key, value, Duration.ofMillis(expiredTime));

        //when
        String returnValue = redisService.getRedisTemplateValue(key);

        //then
        assertThat(value).isEqualTo(returnValue);
        assertThat(value).isEqualTo("email");

    }
    @Test
    void deleteRedis() {
        String key = "token";
        //when
        redisService.deleteRedisTemplateValue(key);
        //then
        String returnValue = redisService.getRedisTemplateValue(key);
        assertThat(returnValue).isNull();

    }
}