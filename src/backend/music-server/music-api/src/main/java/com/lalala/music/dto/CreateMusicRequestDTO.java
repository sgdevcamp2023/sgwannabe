package com.lalala.music.dto;

import com.lalala.music.domain.File;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateMusicRequestDTO {
    private String title;
    private Short playTime;
    private String lyrics;
    private Long albumId;
    private File file;
    private ParticipantsRequestDTO participants;
}
