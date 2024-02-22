package com.sgwannabe.playlistserver.external.feign;

import com.sgwannabe.playlistserver.external.feign.dto.RoomCreateRequestDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChattingService {
    private final FeignChattingClient feignChattingClient;

    public void sendPostDummyRoomCreateRequestToChattingServer(RoomCreateRequestDto roomCreateRequestDto) {

        try {
            feignChattingClient.createDummyRooms(roomCreateRequestDto);
        } catch (FeignException e) {
            log.error("채팅서버 연결 불가");
        }
        log.info("더미 채팅방 생성 성공 room={}", roomCreateRequestDto);
    }
}
