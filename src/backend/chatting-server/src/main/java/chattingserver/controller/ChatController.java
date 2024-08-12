package chattingserver.controller;

import chattingserver.config.kafka.Producers;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.dto.response.CommonAPIMessage;
import chattingserver.dto.response.ReEnterResponseDto;
import chattingserver.service.ChatMessageService;
import chattingserver.service.RoomService;
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Tag(name = "chat", description = "채팅 API")
@RestController
@RequestMapping("v1/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final Producers producers;
    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    @MessageMapping("/send")
    public void sendSocketMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        validateRoomExists(chatMessageDto.getRoomId());
        ChatMessageDto savedMessage = chatMessageService.saveChatMessage(chatMessageDto);
        producers.sendMessage(savedMessage);
    }

    @PostMapping(value = "/message", consumes = "application/json", produces = "application/json")
    public void sendMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        sendSocketMessage(chatMessageDto);
    }

    @GetMapping("/rooms/joined/{roomId}")
    public ResponseEntity<BaseResponse<ReEnterResponseDto>> newMessagesAtRoom(@PathVariable String roomId, @RequestParam String readMsgId) {
        ReEnterResponseDto responseDto = chatMessageService.getReEnterResponse(roomId, readMsgId);
        return ResponseEntity.ok(BaseResponse.success("채팅방 재입장 성공, 새 메시지 조회 성공", responseDto));
    }

    @GetMapping("/history/{roomId}")
    public ResponseEntity<BaseResponse<List<ChatMessageResponseDto>>> allMessagesAtRoom(@PathVariable String roomId) {
        List<ChatMessageResponseDto> allMessages = chatMessageService.getAllMessagesAtRoom(roomId);
        return ResponseEntity.ok(BaseResponse.success("채팅방 히스토리 조회 성공", allMessages));
    }

    @GetMapping("/history")
    public ResponseEntity<BaseResponse<Page<ChatMessageResponseDto>>> chatMessagePagination(
            @RequestParam String roomId, @RequestParam int page) {
        Page<ChatMessageResponseDto> messages = chatMessageService.chatMessagePagination(roomId, page);
        return ResponseEntity.ok(BaseResponse.success("채팅메시지 페이지네이션 성공", messages));
    }

    @MessageMapping("/join")
    public void join(ChatMessageDto message) {
        producers.sendMessage(chatMessageService.join(message));
    }

    private void validateRoomExists(String roomId) {
        if (!roomService.isExistingRoom(roomId)) {
            throw new BusinessException("존재하지 않는 채팅방입니다. 채팅방 id=" + roomId, ErrorCode.UNKNOWN_ERROR);
        }
    }
}
