package com.sgwannabe.playlistserver.external.feign.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicRoomDummyDto {
    private Long id;
    private String title;
    private String artist;
    private String playtime;
    private String thumbnail;
}
