package com.sgwannabe.playlistserver.playlist.service;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistRequestDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistToDtoConverter;
import com.sgwannabe.playlistserver.playlist.exception.NotFoundException;
import com.sgwannabe.playlistserver.playlist.repository.PlaylistRepository;
import com.sgwannabe.playlistserver.playlist.util.KeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final RedisTemplate<String, Playlist> redisTemplate;
    private final PlaylistToDtoConverter converter;

    public PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto) {

        Playlist playlist = Playlist.builder()
                .uid(playlistRequestDto.getUid())
                .userName(playlistRequestDto.getUserName())
                .name(playlistRequestDto.getName())
                .songs(playlistRequestDto.getSongs())
                .thumbnail(playlistRequestDto.getThumnail())
                .build();

        Playlist saved = playlistRepository.save(playlist);

        updateCacheAsync(saved);
        return converter.convert(saved);
    }

    public PlaylistResponseDto getPlaylistById(String id) {
        String key = KeyGenerator.cartKeyGenerate(id);
        Playlist cachedPlaylist = redisTemplate.opsForValue().get(key);
        if (cachedPlaylist != null) {
            return converter.convert(cachedPlaylist);
        }

        Optional<Playlist> playlistOptional = playlistRepository.findById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            updateCacheAsync(playlist);
            return converter.convert(playlist);
        } else {
            throw new NotFoundException("해당하는 플레이리스트가 없습니다 id: " + id);
        }
    }

    @Transactional
    public PlaylistResponseDto updatePlayListById(String id, PlaylistRequestDto playlistRequestDto) {
        Optional<Playlist> existingPlaylistOptional = playlistRepository.findById(id);
        if (existingPlaylistOptional.isPresent()) {
            Playlist existingPlaylist = existingPlaylistOptional.get();

            existingPlaylist.updateName(playlistRequestDto.getName());

            playlistRepository.save(existingPlaylist);
            updateCache(existingPlaylist);

            return converter.convert(existingPlaylist);
        } else {
            throw new NotFoundException("해당하는 플레이리스트가 없습니다 id: " + id);
        }
    }

    @Transactional
    public void deletePlaylistById(String id) {
        Optional<Playlist> existingPlaylistOptional = playlistRepository.findById(id);
        if (existingPlaylistOptional.isPresent()) {
            Playlist existingPlaylist = existingPlaylistOptional.get();

            playlistRepository.deleteById(id);
            deleteCache(existingPlaylist);

        } else {
            throw new NotFoundException("해당하는 플레이리스트가 없습니다 id: " + id);
        }
    }

    @Async
    public CompletableFuture<Void> updateCacheAsync(Playlist playlist) {
        String key = KeyGenerator.cartKeyGenerate(playlist.getId());
        redisTemplate.opsForValue().set(key, playlist, Duration.ofMinutes(10));

        CompletableFuture.runAsync(() -> {
            playlistRepository.save(playlist);
        });

        return CompletableFuture.completedFuture(null);
    }

    private void updateCache(Playlist playlist) {
        String key = KeyGenerator.cartKeyGenerate(playlist.getId());
        redisTemplate.opsForValue().set(key, playlist, Duration.ofMinutes(10));
    }

    private void deleteCache(Playlist playlist) {
        String key = KeyGenerator.cartKeyGenerate(playlist.getId());
        redisTemplate.delete(key);
    }
}
