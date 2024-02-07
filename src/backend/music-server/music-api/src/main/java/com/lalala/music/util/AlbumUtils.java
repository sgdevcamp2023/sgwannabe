package com.lalala.music.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.exception.BusinessException;
import com.lalala.music.exception.ErrorCode;
import com.lalala.music.repository.AlbumRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumUtils {
    public static AlbumEntity findById(Long id, AlbumRepository repository) {
        return repository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALBUM_NOT_FOUND));
    }
}
