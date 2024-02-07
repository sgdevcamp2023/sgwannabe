package com.lalala.music.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.ArtistType;
import com.lalala.music.entity.GenderType;

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

    public static ArtistDTO from(ArtistEntity artist) {
        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getGender(),
                artist.getType(),
                artist.getAgency(),
                artist.getCreatedAt(),
                artist.getUpdatedAt());
    }
}
