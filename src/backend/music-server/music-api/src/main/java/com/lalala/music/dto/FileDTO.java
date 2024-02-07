package com.lalala.music.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.FormatType;
import com.lalala.music.entity.MusicFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FileDTO {
    private String fileUrl;
    private FormatType formatType;

    public static FileDTO from(MusicFile musicFile) {
        return new FileDTO(musicFile.getFileUrl(), musicFile.getFormatType());
    }
}
