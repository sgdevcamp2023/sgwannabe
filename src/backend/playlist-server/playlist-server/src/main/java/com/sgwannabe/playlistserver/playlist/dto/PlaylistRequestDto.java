package com.sgwannabe.playlistserver.playlist.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistRequestDto {
    private Long uid;
    private String userName;
    private String name;
    private String thumnail;
    private String songs; // TODO List<Songs>로 수정
}
