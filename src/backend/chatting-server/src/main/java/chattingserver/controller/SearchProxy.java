package chattingserver.controller;

import chattingserver.dto.request.IndexingRequestMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SEARCH-SERVER")
public interface SearchProxy {
    @PostMapping("/search/index/send")
    void sendIndexingRequest(@RequestBody IndexingRequestMessageDto dto);
}
