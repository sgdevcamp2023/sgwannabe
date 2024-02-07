package com.lalala.music.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.repository.AlbumRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumUtils {
    public static AlbumEntity findById(Long id, AlbumRepository repository) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("앨범을 조회할 수 없습니다."));
    }
}
