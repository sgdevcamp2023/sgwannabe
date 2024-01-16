package com.lalala.music.repository;

import com.lalala.music.entity.MusicLyricistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicLyricistRepository extends JpaRepository<MusicLyricistEntity, Long> {
    List<MusicLyricistEntity> findAllByMusicId(Long musicId);
    List<MusicLyricistEntity> findAllByArtistId(Long artistId);
}

