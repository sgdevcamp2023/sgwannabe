package com.sgwannabe.playlistserver.external.feign;

import com.lalala.response.BaseResponse;
import com.sgwannabe.playlistserver.external.feign.dto.MusicDTO;
import com.sgwannabe.playlistserver.external.feign.dto.MusicRetrieveRequestDTO;
import com.sgwannabe.playlistserver.external.feign.dto.RoomCreateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CHATTING-SERVER")
public interface FeignChattingClient {

    @PostMapping("/v1/api/rooms/create")
    void createDummyRooms(@RequestBody RoomCreateRequestDto request);

}
