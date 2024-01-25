package com.sgwannabe.playlistserver.playlist.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaylistResponseDto {
    private Long uid;
    private String userName;
    private String id;
    private String name;
    private String thumnail;
    private String songs;

}
