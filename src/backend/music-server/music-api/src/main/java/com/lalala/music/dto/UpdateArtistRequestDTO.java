package com.lalala.music.dto;

import com.lalala.music.domain.*;

import com.lalala.music.domain.ArtistType;
import com.lalala.music.domain.GenderType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateArtistRequestDTO {
    private String name;
    private GenderType gender;
    private ArtistType type;
    private String agency;
}
