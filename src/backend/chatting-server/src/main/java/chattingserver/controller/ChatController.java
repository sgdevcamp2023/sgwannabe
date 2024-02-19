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
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/chat")
@Slf4j
public class ChatController {


    private final Producers producers;
    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    @MessageMapping("/send")
    @Operation(summary = "웹소켓 메시지 전송")
    public void sendSocketMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        if (!roomService.isExistingRoom(chatMessageDto.getRoomId())) {
            throw new BusinessException("존재하지 않는 채팅방입니다. 채팅방 id=" + chatMessageDto.getRoomId(), ErrorCode.UNKNOWN_ERROR);
        }
        ChatMessageDto savedMessage = chatMessageService.saveChatMessage(chatMessageDto);
        producers.sendMessage(savedMessage);

        log.info("메시지 전송 완료 - message={}", chatMessageDto);
    }

    @Operation(summary = "메시지 전송")
    @PostMapping(value = "/message", consumes = "application/json", produces = "application/json")
    public void sendMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        if (!roomService.isExistingRoom(chatMessageDto.getRoomId())) {
            throw new BusinessException("메시지 전송 에러 - 존재하지 않는 채팅방입니다. 채팅방 id=" + chatMessageDto.getRoomId(), ErrorCode.UNKNOWN_ERROR);

        }
        ChatMessageDto savedMessage = chatMessageService.saveChatMessage(chatMessageDto);

        producers.sendMessage(savedMessage);

        log.info("메시지 전송 완료 - message={}", chatMessageDto);
    }

    @Operation(summary = "참여중인 채팅방 재입장, 새 메시지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅방 입장, 새 메시지 조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/rooms/joined/{roomId}")  // TODO 웹소켓 연결 ??
    public ResponseEntity<BaseResponse<ReEnterResponseDto>> newMessagesAtRoom(@PathVariable String roomId, @RequestParam String readMsgId) {


        ReEnterResponseDto responseDto = ReEnterResponseDto.builder()
                .beforeMessages(chatMessageService.getMessagesBefore(roomId, readMsgId))
                .newMessages(chatMessageService.getNewMessages(roomId, readMsgId))
                .currentMusicId(chatMessageService.getCurrentMusicId(roomId))
                .build();

        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 재입장 성공, 새 메시지 조회 성공", responseDto));
    }

    @Operation(summary = "특정 채팅방 히스토리 조회", description = "내림차순으로 특정 채팅방의 전체 메세지를 조회합니다.")
    @GetMapping("/history/{roomId}")
    public ResponseEntity<BaseResponse<List<ChatMessageResponseDto>>> allMessagesAtRoom(@PathVariable String roomId) {
        List<ChatMessageResponseDto> allMessagesAtRoom = chatMessageService.getAllMessagesAtRoom(roomId);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 히스토리 조회 성공", allMessagesAtRoom));
    }

    @Operation(summary = "채팅 메시지 Pagination", description = "내림차순으로 해당 채팅방 메시지 Pagination, 사이즈 N = 20 고정")
    @GetMapping("/history")
    public ResponseEntity<BaseResponse<Page<ChatMessageResponseDto>>> chatMessagePagination(
            @RequestParam String roomId,
            @Parameter(description = "첫 페이지는 0부터 시작") @RequestParam int page) {
        Page<ChatMessageResponseDto> responseDtos = chatMessageService.chatMessagePagination(roomId, page);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅메시지 페이지네이션 성공", responseDtos));
    }

    @MessageMapping("/join")
    public void join(ChatMessageDto message) {

        producers.sendMessage(chatMessageService.join(message));
    }

}
