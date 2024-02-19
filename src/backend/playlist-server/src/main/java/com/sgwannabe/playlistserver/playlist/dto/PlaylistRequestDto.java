package com.sgwannabe.playlistserver.playlist.dto;

import com.sgwannabe.playlistserver.music.domain.Music;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PlaylistRequestDto {
    @NotBlank(message = "uid는 공백일 수 없습니다.")
    private Long uid;
    @NotBlank(message = "username은 공백일 수 없습니다.")
    private String userName;
    @NotBlank(message = "playlist name은 공백일 수 없습니다.")
    private String name;
    @NotBlank(message = "thumbnail은 공백일 수 없습니다.")
    private String thumbnail;
//    @NotBlank
    private List<Long> musics;
}
