package com.sgwannabe.playlistserver.external.feign.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistRoomDummyDto {

    private String id;
    private String name;

    private Long playlistOwnerId;
    private String playlistOwnerNickName;
    private String playlistOwnerProfileImage;

    private List<MusicRoomDummyDto> musics;
}
