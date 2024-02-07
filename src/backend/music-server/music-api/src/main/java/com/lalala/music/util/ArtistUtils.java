package com.lalala.music.util;

import com.lalala.music.exception.BusinessException;
import com.lalala.music.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.repository.ArtistRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistUtils {
    public static ArtistEntity findById(Long id, ArtistRepository repository) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.ARTIST_NOT_FOUND));
    }
}
