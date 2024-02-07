package com.lalala.music.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.MusicEntity;
import com.lalala.music.repository.MusicRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicUtils {
    public static MusicEntity findById(Long id, MusicRepository repository) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("음원을 조회할 수 없습니다."));
    }
}
