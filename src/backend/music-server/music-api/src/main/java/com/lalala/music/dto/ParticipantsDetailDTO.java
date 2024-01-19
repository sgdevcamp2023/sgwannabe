package com.lalala.music.dto;

import com.lalala.music.entity.ArtistEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParticipantsDetailDTO {
    private final ArtistDTO artist;

    public static ParticipantsDetailDTO from(
            ArtistEntity artist
    ) {
        return new ParticipantsDetailDTO(
                ArtistDTO.from(artist)
        );
    }
}
