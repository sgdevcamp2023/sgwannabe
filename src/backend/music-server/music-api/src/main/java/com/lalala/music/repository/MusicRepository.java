package com.lalala.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalala.music.entity.MusicEntity;

public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
    List<MusicEntity> findAllByAlbumId(Long albumId);
}
