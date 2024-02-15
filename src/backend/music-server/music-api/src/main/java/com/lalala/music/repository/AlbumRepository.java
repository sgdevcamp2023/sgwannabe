package com.lalala.music.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lalala.music.entity.AlbumEntity;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findByTitleAndArtistId(String title, Long artistId);
}
