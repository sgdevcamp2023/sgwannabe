package com.sgwannabe.playlistserver.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicOrderChangeRequestDto {
    @NotBlank(message = "fromIndex는 공백일 수 없습니다.")
    private int fromIndex;
    @NotBlank(message = "toIndex는 공백일 수 없습니다.")
    private int toIndex;
}
