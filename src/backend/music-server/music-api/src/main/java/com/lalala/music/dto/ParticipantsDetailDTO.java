package com.lalala.music.dto;

import com.lalala.music.entity.ArtistEntity;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParticipantsDetailDTO {
    private final ArtistDTO artist;

    private final Set<ArtistDTO> composers;
    private final Set<ArtistDTO> lyricists;
    private final Set<ArtistDTO> arrangers;

    public static ParticipantsDetailDTO from(
            ArtistEntity artist,
            Set<ArtistEntity> composers,
            Set<ArtistEntity> lyricists,
            Set<ArtistEntity> arrangers
    ) {
        return new ParticipantsDetailDTO(
                ArtistDTO.from(artist),
                composers.stream().map(ArtistDTO::from).collect(Collectors.toSet()),
                lyricists.stream().map(ArtistDTO::from).collect(Collectors.toSet()),
                arrangers.stream().map(ArtistDTO::from).collect(Collectors.toSet())
        );
    }
}
