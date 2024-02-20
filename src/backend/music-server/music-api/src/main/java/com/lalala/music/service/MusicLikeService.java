package com.lalala.music.service;

import com.lalala.music.entity.MusicEntity;
import com.lalala.music.entity.MusicLikeEntity;
import com.lalala.music.repository.MusicLikeRepository;
import com.lalala.music.repository.MusicRepository;
import com.lalala.music.util.MusicUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicLikeService {
    private final MusicLikeRepository repository;
    private final MusicRepository musicRepository;

    @Transactional
    public boolean create(Long userId, Long musicId) {
        MusicEntity music = MusicUtils.findById(musicId, musicRepository);

        Optional<MusicLikeEntity> musicLike = repository.findByUserIdAndMusicId(userId, music.getId());
        if (musicLike.isPresent()) {
            repository.delete(musicLike.get());

            music.decreaseLikeCount();
            musicRepository.save(music);

            return false;
        } else {
            MusicLikeEntity createdMusicLike = new MusicLikeEntity(userId, music.getId());
            repository.save(createdMusicLike);

            music.increaseLikeCount();
            musicRepository.save(music);

            return true;
        }
    }
}
