package com.sgwannabe.playlistserver.external.feign.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArtistDTO {
    private final Long id;
    private final String name;
    private final GenderType genderType;
    private final ArtistType type;
    private final String agency;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
