package com.lalala.music.mapper;

import com.lalala.music.domain.*;

import com.lalala.music.entity.ArtistEntity;

public final class ArtistMapper {
    public static Artist from(ArtistEntity artist) {
        return new Artist(
                artist.getId(),
                artist.getName(),
                artist.getGender(),
                artist.getType(),
                artist.getAgency(),
                artist.getCreatedAt(),
                artist.getUpdatedAt());
    }
}
