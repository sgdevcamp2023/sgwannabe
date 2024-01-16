package com.lalala.music.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ParticipantsRequestDTO {
    private Long artistId;

    private List<Long> composerIds;
    private List<Long> lyricistIds;
    private List<Long> arrangerIds;
}