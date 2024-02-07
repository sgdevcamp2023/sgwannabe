package com.lalala.music.util;

import com.lalala.music.exception.BusinessException;
import com.lalala.music.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.MusicEntity;
import com.lalala.music.repository.MusicRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicUtils {
    public static MusicEntity findById(Long id, MusicRepository repository) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.MUSIC_NOT_FOUND));
    }
}
