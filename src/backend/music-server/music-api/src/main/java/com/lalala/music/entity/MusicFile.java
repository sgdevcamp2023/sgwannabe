package com.lalala.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class MusicFile {
    @Column(name = "file_url", nullable = false, columnDefinition = "VARCHAR(255) default ''")
    Long fileUrl;

    @Column(name = "format_type", nullable = false, length = 5, columnDefinition = "CHAR(5) default 'MP3'")
    @Enumerated(EnumType.STRING)
    FormatType formatType;
}
