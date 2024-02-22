package com.sgwannabe.playlistserver.external.feign;


import com.sgwannabe.playlistserver.external.feign.dto.PlaylistRoomDummyDto;
import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlaylistToPlaylistDummyDtoConverter {

    private final MusicToMusicDummyDtoConverter converter;
    public PlaylistRoomDummyDto convert(Playlist playlist) {
        return PlaylistRoomDummyDto.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .playlistOwnerId(playlist.getUid())
                .playlistOwnerNickName(playlist.getUserName())
                .musics(playlist.getMusics().stream().map(converter::convert).collect(Collectors.toList()))
                .build();
    }
}
