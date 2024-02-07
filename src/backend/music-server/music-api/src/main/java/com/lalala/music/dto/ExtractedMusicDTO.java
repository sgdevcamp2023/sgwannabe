package com.lalala.music.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ExtractedMusicDTO {
    private String title;
    private String artist;
    private String album;
    private String lyrics;
    private Long playTime;

    private byte[] coverData;
    private String coverExtension;
}
