package com.sgwannabe.playlistserver.playlist.util;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.dto.PlaylistResponseDto;

public class PlaylistToDtoConverter {
    public PlaylistResponseDto convert(Playlist playlist) {

        return PlaylistResponseDto.builder()
                .uid(playlist.getUid())
                .userName(playlist.getUserName())
                .id(playlist.getId())
                .name(playlist.getName())
                .thumbnail(playlist.getThumbnail())
                .musics(playlist.getMusics())
                .build();
    }
}
