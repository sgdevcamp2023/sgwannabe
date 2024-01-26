package com.sgwannabe.playlistserver.music.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MusicRequestDto {
    private Long id;
    private String title;
    private Long artistId;
    private String artist;
    private Long albumId;
    private String album;
    private String thumbnail;
    private String playtime;

}
