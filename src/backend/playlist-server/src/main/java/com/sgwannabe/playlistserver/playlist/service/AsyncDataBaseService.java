package com.sgwannabe.playlistserver.playlist.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncDatabaseService {

    private final PlaylistRepository playlistRepository;

    @Async
    @Transactional
    public CompletableFuture<Playlist> savePlaylistAsync(Playlist playlist) {
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return CompletableFuture.completedFuture(savedPlaylist);
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deletePlaylistAsync(String id) {
        playlistRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}
