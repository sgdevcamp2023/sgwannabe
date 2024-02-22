package com.sgwannabe.playlistserver.playlist.util;

import com.sgwannabe.playlistserver.external.feign.ChattingService;
import com.sgwannabe.playlistserver.external.feign.PlaylistToPlaylistDummyDtoConverter;
import com.sgwannabe.playlistserver.external.feign.dto.PlaylistRoomDummyDto;
import com.sgwannabe.playlistserver.external.feign.dto.RoomCreateRequestDto;
import com.sgwannabe.playlistserver.playlist.domain.Playlist;
import com.sgwannabe.playlistserver.playlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DummyInitializer implements ApplicationRunner {

    private final PlaylistRepository playlistRepository;
    private final ChattingService chattingService;
    private final PlaylistToPlaylistDummyDtoConverter converter;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        PlaylistRoomDummyDto playlist1 = converter.convert(playlistRepository.findById("65d6b0ef26713677d7062f50").get());
        playlist1.setPlaylistOwnerProfileImage("");

        PlaylistRoomDummyDto playlist2 = converter.convert(playlistRepository.findById("65d6ae6e26713677d7062f4e").get());
        playlist2.setPlaylistOwnerProfileImage("https://cdn.discordapp.com/avatars/554274257308483584/75a91b800109785e1e1761f1ced07032.webp?size=32");

        PlaylistRoomDummyDto playlist3 = converter.convert(playlistRepository.findById("65d6aedf26713677d7062f4f").get());
        playlist3.setPlaylistOwnerProfileImage("https://cdn.discordapp.com/avatars/747483070713692251/c6a359f6b0284edf23d4d6de6a4435f7.webp?size=32");

        PlaylistRoomDummyDto playlist4 = converter.convert(playlistRepository.findById("65d6b1e626713677d7062f51").get());
        playlist4.setPlaylistOwnerProfileImage("https://cdn.discordapp.com/avatars/990139242468478976/aeffadd53b7e25f5ebee29091377b4ee.webp?size=32");

        PlaylistRoomDummyDto playlist5 = converter.convert(playlistRepository.findById("65d6d47526713677d7062f52").get());
        playlist5.setPlaylistOwnerProfileImage("");


        RoomCreateRequestDto dummy1 = RoomCreateRequestDto.builder()
                .uid(6L)
                .nickName("민주")
                .userProfileImage("")
                .playlist(playlist1)
                .build();

        RoomCreateRequestDto dummy2 = RoomCreateRequestDto.builder()
                .uid(5L)
                .nickName("수아")
                .userProfileImage("https://cdn.discordapp.com/avatars/554274257308483584/75a91b800109785e1e1761f1ced07032.webp?size=32")
                .playlist(playlist2)
                .build();

        RoomCreateRequestDto dummy3 = RoomCreateRequestDto.builder()
                .uid(4L)
                .nickName("선재")
                .userProfileImage("https://cdn.discordapp.com/avatars/747483070713692251/c6a359f6b0284edf23d4d6de6a4435f7.webp?size=32")
                .playlist(playlist3)
                .build();

        RoomCreateRequestDto dummy4 = RoomCreateRequestDto.builder()
                .uid(7L)
                .nickName("소연")
                .userProfileImage("https://cdn.discordapp.com/avatars/990139242468478976/aeffadd53b7e25f5ebee29091377b4ee.webp?size=32")
                .playlist(playlist4)
                .build();

        RoomCreateRequestDto dummy5 = RoomCreateRequestDto.builder()
                .uid(1L)
                .nickName("jjanggu")
                .userProfileImage("")
                .playlist(playlist5)
                .build();

        chattingService.sendPostDummyRoomCreateRequestToChattingServer(dummy1);
        chattingService.sendPostDummyRoomCreateRequestToChattingServer(dummy2);
        chattingService.sendPostDummyRoomCreateRequestToChattingServer(dummy3);
        chattingService.sendPostDummyRoomCreateRequestToChattingServer(dummy4);
        chattingService.sendPostDummyRoomCreateRequestToChattingServer(dummy5);
    }
}
