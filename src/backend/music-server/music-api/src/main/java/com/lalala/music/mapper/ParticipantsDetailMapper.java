package com.lalala.music.mapper;

import com.lalala.music.domain.ParticipantsDetail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.ArtistEntity;

@Getter
@RequiredArgsConstructor
public class ParticipantsDetailMapper {
    public static ParticipantsDetail from(ArtistEntity artist) {
        return new ParticipantsDetail(ArtistMapper.from(artist));
    }
}
