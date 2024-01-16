package com.lalala.music.dto;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.ArtistType;
import com.lalala.music.entity.GenderType;
import com.lalala.music.entity.MusicComposerEntity;
import com.lalala.music.entity.MusicEntity;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

    private final Set<MusicDTO> composedMusics;
    private final Set<MusicDTO> writtenMusics;
    private final Set<MusicDTO> arrangedMusics;

    public static ArtistDetailDTO from(
            ArtistEntity artist,
            Set<MusicEntity> composedMusics,
            Set<MusicEntity> writtenMusics,
            Set<MusicEntity> arrangedMusics,
            Map<Long, AlbumEntity> albumMap
    ) {
        return new ArtistDetailDTO(
                artist.getId(),
                artist.getName(),
                artist.getGender(),
                artist.getType(),
                artist.getAgency(),
                artist.getCreatedAt(),
                artist.getUpdatedAt(),
                composedMusics.stream()
                        .map(music ->
                        MusicDTO.from(
                                music,
                                albumMap.get(music.getAlbumId()),
                                artist
                        )
                ).collect(Collectors.toUnmodifiableSet()),
                writtenMusics.stream()
                        .map(music ->
                                MusicDTO.from(
                                        music,
                                        albumMap.get(music.getAlbumId()),
                                        artist
                                )
                        ).collect(Collectors.toUnmodifiableSet()),
                arrangedMusics.stream()
                        .map(music ->
                                MusicDTO.from(
                                        music,
                                        albumMap.get(music.getAlbumId()),
                                        artist
                                )
                        ).collect(Collectors.toUnmodifiableSet())
        );
    }
}
