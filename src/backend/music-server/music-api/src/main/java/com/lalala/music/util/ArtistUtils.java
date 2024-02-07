package com.lalala.music.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.repository.ArtistRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistUtils {
    public static ArtistEntity findById(Long id, ArtistRepository repository) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("아티스트를 조회할 수 없습니다."));
    }
}
