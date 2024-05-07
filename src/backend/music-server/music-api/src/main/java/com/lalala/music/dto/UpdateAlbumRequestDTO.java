package com.lalala.music.dto;

import com.lalala.music.domain.*;

import com.lalala.music.domain.AlbumType;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateAlbumRequestDTO {
    private String title;
    private String coverUrl;
    private AlbumType type;
    private LocalDateTime releasedAt;
    private Long artistId;
}
