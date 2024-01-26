package com.sgwannabe.playlistserver.playlist.dto;

import com.sgwannabe.playlistserver.music.domain.Music;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistRequestDto {
    private Long uid;
    private String userName;
    private String name;
    private String thumbnail;
    private List<Music> musics; // TODO List<Music>로 수정
}
