package com.sgwannabe.playlistserver.global.config;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.util.PlaylistRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Playlist> redisTemplate(RedisConnectionFactory connectionFactory,
                                                         PlaylistRedisSerializer playlistRedisSerializer) {
        RedisTemplate<String, Playlist> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(playlistRedisSerializer);
        redisTemplate.setHashValueSerializer(playlistRedisSerializer);

        return redisTemplate;
    }
}

