package com.lalala.music.entity;

import com.lalala.music.domain.*;

import com.lalala.music.domain.FormatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicFile {
    @Column(name = "file_url", nullable = false, columnDefinition = "VARCHAR(255) default ''")
    String fileUrl;

    @Column(
            name = "format_type",
            nullable = false,
            length = 5,
            columnDefinition = "VARCHAR(5) default 'MP3'")
    @Enumerated(EnumType.STRING)
    FormatType formatType;
}
