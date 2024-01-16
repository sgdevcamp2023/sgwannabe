package com.lalala.music.repository;

import com.lalala.music.entity.MusicArrangerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicArrangerRepository extends JpaRepository<MusicArrangerEntity, Long> {
    List<MusicArrangerEntity> findAllByMusicId(Long musicId);
    List<MusicArrangerEntity> findAllByArtistId(Long artistId);
}
