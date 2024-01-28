package com.sgwannabe.playlistserver.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MusicRequestDto {
    @NotBlank(message = "음원 id는 공백일 수 없습니다.")
    private Long id;
    @NotBlank(message = "음원 title은 공백일 수 없습니다.")
    private String title;
    @NotBlank(message = "음원 artist id는 공백일 수 없습니다.")
    private Long artistId;
    @NotBlank(message = "음원 artist명은 공백일 수 없습니다.")
    private String artist;
    @NotBlank(message = "음원 album id는 공백일 수 없습니다.")
    private Long albumId;
    @NotBlank(message = "음원 id는 공백일 수 없습니다.")
    private String album;
    @NotBlank(message = "음원 thumbnail은 공백일 수 없습니다.")
    private String thumbnail;
    @NotBlank(message = "음원 playtime은 공백일 수 없습니다.")
    private String playtime;

}
