package com.lalala.music.dto;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.AlbumType;
import com.lalala.music.entity.ArtistEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumDTO {
    private final Long id;
    private final AlbumType type;
    private final String title;
    private final LocalDateTime releasedAt;

    private final ArtistDTO artist;

    public static AlbumDTO from(
            AlbumEntity album,
            ArtistEntity artist
    ) {
        return new AlbumDTO(
                album.getId(),
                album.getType(),
                album.getTitle(),
                album.getReleasedAt(),
                ArtistDTO.from(artist)
        );
    }
}
