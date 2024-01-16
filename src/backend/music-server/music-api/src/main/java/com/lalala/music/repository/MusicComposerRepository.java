package com.lalala.music.repository;

import com.lalala.music.entity.MusicComposerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicComposerRepository extends JpaRepository<MusicComposerEntity, Long> {
    List<MusicComposerEntity> findAllByMusicId(Long musicId);
    List<MusicComposerEntity> findAllByArtistId(Long artistId);
}

