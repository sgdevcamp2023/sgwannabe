package com.sgwannabe.playlistserver.music.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicOrderChangeRequestDto {
    private int fromIndex;
    private int toIndex;
}
