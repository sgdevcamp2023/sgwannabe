package com.lalala.music.dto;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.AlbumType;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumDetailDTO {
    private final Long id;
    private final AlbumType type;
    private final String title;
    private final String coverURL;
    private final LocalDateTime releasedAt;

    private final ArtistDTO artist;
    private final List<MusicDTO> musics;

    public static AlbumDetailDTO from(
            AlbumEntity album,
            ArtistEntity artist,
            List<MusicEntity> musics
    ) {
        return new AlbumDetailDTO(
                album.getId(),
                album.getType(),
                album.getTitle(),
                album.getCoverURL(),
                album.getReleasedAt(),
                ArtistDTO.from(artist),
                musics.stream().map(
                        music -> MusicDTO.from(
                            music,
                            album,
                            artist
                        )
                ).toList()
        );
    }

    public static AlbumDetailDTO from(
            AlbumEntity album,
            ArtistEntity artist
    ) {
        return from(
                album,
                artist,
                List.of()
        );
    }
}
