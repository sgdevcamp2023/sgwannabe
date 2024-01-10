package com.lalala.music.repository;

import com.lalala.music.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<MusicEntity, Long> {
}
