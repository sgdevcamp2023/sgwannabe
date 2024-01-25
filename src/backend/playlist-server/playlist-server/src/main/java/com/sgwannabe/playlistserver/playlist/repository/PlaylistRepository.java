package com.sgwannabe.playlistserver.playlist.repository;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String> {
    @Override
    <S extends Playlist> S save(S entity);

    @Override
    Optional<Playlist> findById(String s);

    @Override
    void deleteById(String s);
}