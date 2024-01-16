package com.lalala.music.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateMusicRequestDTO {
    private String title;
    private Short playTime;
    private String lyrics;
    private Long albumId;
    private FileDTO file;
    private ParticipantsRequestDTO participants;
}
