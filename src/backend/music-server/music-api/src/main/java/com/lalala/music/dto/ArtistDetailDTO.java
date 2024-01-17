package com.lalala.music.dto;

import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.ArtistType;
import com.lalala.music.entity.GenderType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArtistDetailDTO {
    private final Long id;
    private final String name;
    private final GenderType genderType;
    private final ArtistType type;
    private final String agency;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static ArtistDetailDTO from(
            ArtistEntity artist
    ) {
        return new ArtistDetailDTO(
                artist.getId(),
                artist.getName(),
                artist.getGender(),
                artist.getType(),
                artist.getAgency(),
                artist.getCreatedAt(),
                artist.getUpdatedAt()
        );
    }
}
