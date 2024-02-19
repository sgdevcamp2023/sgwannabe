package com.sgwannabe.playlistserver.external.feign;

import com.lalala.response.BaseResponse;
import com.sgwannabe.playlistserver.external.feign.dto.MusicDTO;
import com.sgwannabe.playlistserver.external.feign.dto.MusicRetrieveRequestDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MUSIC-SERVER")
public interface FeignMusicClient {

    @PostMapping("/v1/api/musics/retrieve")
    BaseResponse<List<MusicDTO>> getMusicFromIds(@RequestBody MusicRetrieveRequestDTO request);

}
