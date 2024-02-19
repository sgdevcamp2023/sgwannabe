package com.lalala.music.dto;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.AlbumType;
import com.lalala.music.entity.ArtistEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MusicRetrieveRequestDTO {
    private List<Long> ids;
}

