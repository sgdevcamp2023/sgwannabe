package com.lalala.music.mapper;

import com.lalala.music.domain.ArtistDetail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.ArtistEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArtistDetailMapper {
    public static ArtistDetail from(ArtistEntity artist) {
        return new ArtistDetail(
                artist.getId(),
                artist.getName(),
                artist.getGender(),
                artist.getType(),
                artist.getAgency(),
                artist.getCreatedAt(),
                artist.getUpdatedAt());
    }
}
