package chattingserver.repository;

import chattingserver.dto.request.ReadMessageUpdateRequestDto;
import com.mongodb.client.result.UpdateResult;

public interface RoomRepositoryCustom {

    void exitRoom(String roomId, Long uid);
    UpdateResult updateLastReadMsgId(ReadMessageUpdateRequestDto requestDto);
}
