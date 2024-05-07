package com.lalala.music.mapper;

import com.lalala.music.domain.File;

import com.lalala.music.entity.MusicFile;

public class MusicFileMapper {
    public static File from(MusicFile musicFile) {
        return new File(musicFile.getFileUrl(), musicFile.getFormatType());
    }
}
