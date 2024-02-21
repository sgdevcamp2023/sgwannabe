package com.lalala.music.repository;

import com.lalala.music.entity.MusicLikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicLikeRepository extends JpaRepository<MusicLikeEntity, Long> {
    Optional<MusicLikeEntity> findByUserIdAndMusicId(Long userId, Long musicId);
}
