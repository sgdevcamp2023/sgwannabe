package chattingserver.controller;

import chattingserver.config.kafka.Producers;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.dto.response.CommonAPIMessage;
import chattingserver.service.ChatMessageService;
import chattingserver.service.RoomService;
import chattingserver.util.constant.ErrorCode;
import chattingserver.util.exception.CustomAPIException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@Tag(name = "chat", description = "채팅 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {


    private final Producers producers;
    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    @MessageMapping("/chat/pub")
    @Operation(summary = "웹소켓 메시지 전송")
    public void sendSocketMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        if (!roomService.isExistingRoom(chatMessageDto.getRoomId())) {
            log.error("메시지 전송 에러 : 존재하지 않는 방입니다. roomId={}", chatMessageDto.getRoomId());
            throw new CustomAPIException(ErrorCode.ROOM_NOT_FOUND_ERROR, "채팅방 id=" + chatMessageDto.getRoomId());
        }

        ChatMessageResponseDto savedMessage = chatMessageService.saveChatMessage(chatMessageDto);
        producers.sendMessage(chatMessageDto);

        log.info("메시지 전송 완료 - message={}", chatMessageDto);
    }

    @Operation(summary = "메시지 전송")
    @PostMapping(value = "/api/v1/message", consumes = "application/json", produces = "application/json")
    public void sendMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        if (!roomService.isExistingRoom(chatMessageDto.getRoomId())) {
            log.error("메시지 전송 에러 : 존재하지 않는 방입니다. roomId={}", chatMessageDto.getRoomId());
            throw new CustomAPIException(ErrorCode.ROOM_NOT_FOUND_ERROR, "채팅방 id=" + chatMessageDto.getRoomId());
        }
        ChatMessageResponseDto savedMessage = chatMessageService.saveChatMessage(chatMessageDto);

        producers.sendMessage(chatMessageDto);

        log.info("메시지 전송 완료 - message={}", chatMessageDto);
    }

    @Operation(summary = "채팅방 새 메시지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 새 메시지 조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/api/v1/new-message/{roomId}/{readMsgId}")
    public ResponseEntity<CommonAPIMessage> newMessagesAtRoom(@PathVariable String roomId, @PathVariable String readMsgId) {
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(chatMessageService.getNewMessages(roomId, readMsgId));
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @Operation(summary = "특정 채팅방 히스토리 조회", description = "내림차순으로 특정 채팅방의 전체 메세지를 조회합니다.")
    @GetMapping("/api/v1/history/{roomId}")
    public ResponseEntity<CommonAPIMessage> allMessagesAtRoom(@PathVariable String roomId) {
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(chatMessageService.getAllMessagesAtRoom(roomId));
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @Operation(summary = "채팅 메시지 Pagination", description = "내림차순으로 해당 채팅방 메시지 Pagination, 사이즈 N = 12 고정")
    @GetMapping("/history")
    public ResponseEntity<CommonAPIMessage> chatMessagePagination(
            @RequestParam String roomId,
            @Parameter(description = "첫 페이지는 0부터 시작") @RequestParam int page) {
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(chatMessageService.chatMessagePagination(roomId, page));
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @MessageMapping("/api/v1/join")
    public void join(ChatMessageDto message) {

        producers.sendMessage(chatMessageService.join(message));
    }

    @MessageMapping("/api/v1/leave")
    public void leave(ChatMessageDto message) {

        producers.sendMessage(chatMessageService.leave(message));
    }
}
