package com.sgwannabe.playlistserver.external.feign.dto;

import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class RoomCreateRequestDto {

    @NotBlank
    private Long uid;
    @NotBlank
    private String nickName;
    @NotBlank
    private String userProfileImage;
    @NotBlank
    private PlaylistRoomDummyDto playlist;

}

