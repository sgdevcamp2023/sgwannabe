package com.lalala.music.service;

import com.lalala.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository repository;
}
