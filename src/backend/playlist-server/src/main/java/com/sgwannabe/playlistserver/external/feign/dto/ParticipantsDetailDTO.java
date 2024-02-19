package com.sgwannabe.playlistserver.external.feign.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParticipantsDetailDTO {
    private final ArtistDTO artist;
}
