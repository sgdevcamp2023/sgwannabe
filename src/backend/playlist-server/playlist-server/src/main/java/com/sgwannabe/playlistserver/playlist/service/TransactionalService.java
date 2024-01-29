package com.sgwannabe.playlistserver.playlist.service;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.repository.PlaylistRepository;
import com.sgwannabe.playlistserver.playlist.util.KeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TransactionalService {

    private final PlaylistRepository playlistRepository;
    private final RedisTemplate<String, Playlist> redisTemplate;

    @Transactional
    public void updateCacheAsync(Playlist playlist) {
        String key = KeyGenerator.playlistKeyGenerate(playlist.getId());
        redisTemplate.opsForValue().set(key, playlist, Duration.ofMinutes(10));
        log.info("redis 저장 완료");
        asyncUpdateDatabase(playlist);
    }

    @Async
    public void asyncUpdateDatabase(Playlist playlist) {
        playlistRepository.save(playlist);
    }

}
