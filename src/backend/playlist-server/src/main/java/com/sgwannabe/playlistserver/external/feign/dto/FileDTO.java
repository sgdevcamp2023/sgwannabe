package com.sgwannabe.playlistserver.external.feign.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FileDTO {
    private String fileUrl;
    private FormatType formatType;
}
