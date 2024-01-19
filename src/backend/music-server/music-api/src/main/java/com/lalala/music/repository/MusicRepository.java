package com.lalala.music.repository;

import com.lalala.music.entity.MusicEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicEntity, Long> {
    List<MusicEntity> findAllByAlbumId(Long albumId);
}
