package com.lalala.music.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.lalala.music.entity.ArtistType;
import com.lalala.music.entity.GenderType;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateArtistRequestDTO {
    private String name;
    private GenderType gender;
    private ArtistType type;
    private String agency;
}
