package com.sgwannabe.playlistserver.music.domain;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private Long artistId;
    private String artist;
    private Long albumId;
    private String album;
    private String thumbnail;
    private String playtime;
}
