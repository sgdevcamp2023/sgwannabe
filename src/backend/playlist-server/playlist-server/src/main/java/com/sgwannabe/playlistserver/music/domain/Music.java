package com.sgwannabe.playlistserver.music.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Music {
    private Long id;
    private String title;
    private Long artistId;
    private String artist;
    private Long albumId;
    private String album;
    private String thumbnail;
    private String playtime;
}
