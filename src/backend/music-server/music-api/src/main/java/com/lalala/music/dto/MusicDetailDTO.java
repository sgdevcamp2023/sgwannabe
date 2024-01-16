package com.lalala.music.dto;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicDetailDTO {
    private final Long id;
    private final String title;
    private final Short playTime;
    private final String lyrics;
    private final AlbumDTO album;
    private final FileDTO file;
    private final ParticipantsDetailDTO participants;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MusicDetailDTO from(
            MusicEntity music,
            ArtistEntity artist,
            AlbumEntity album,
            Set<ArtistEntity> composers,
            Set<ArtistEntity> lyricists,
            Set<ArtistEntity> arrangers
    ) {
        return new MusicDetailDTO(
                music.getId(),
                music.getTitle(),
                music.getPlayTime(),
                music.getLyrics(),
                AlbumDTO.from(album, artist),
                FileDTO.from(music.getFile()),
                ParticipantsDetailDTO.from(
                        artist,
                        composers,
                        lyricists,
                        arrangers
                ),
                music.getCreatedAt(),
                music.getUpdatedAt()
        );
    }
}
