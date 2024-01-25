package com.sgwannabe.playlistserver.playlist.dto;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;

public class PlaylistToDtoConverter {
    public PlaylistResponseDto convert(Playlist playlist) {

        return PlaylistResponseDto.builder()
                .uid(playlist.getUid())
                .userName(playlist.getUserName())
                .id(playlist.getId())
                .name(playlist.getName())
                .thumnail(playlist.getThumbnail())
                .songs(playlist.getSongs())
                .build();
    }
}
