package chattingserver.service;

import chattingserver.controller.SearchProxy;
import chattingserver.dto.request.IndexingRequestMessageDto;
import chattingserver.dto.response.RoomResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Async("search")
@RequiredArgsConstructor
public class SearchService {
    private final SearchProxy searchProxy;

    public void sendIndexingRequestToSearchServer(RoomResponseDto roomResponseDto) {

        try {
            searchProxy.sendIndexingRequest(IndexingRequestMessageDto.builder()
                    .roomId(roomResponseDto.getId())
                    .roomName(roomResponseDto.getRoomName())
                    .playlistId(roomResponseDto.getPlaylist().getId())
                    .thumbnailImage(roomResponseDto.getThumbnailImage())
                    .build());
        } catch (FeignException e) {
            log.error("searchProxy.sendIndexingRequest, 검색 서버 연결 불가");
        }
        log.info("검색 서버에 인덱싱 요청 성공");
    }
}
