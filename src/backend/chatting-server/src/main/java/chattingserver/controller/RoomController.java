package chattingserver.controller;

import chattingserver.config.kafka.Producers;
import chattingserver.dto.request.IndexingRequestMessageDto;
import chattingserver.dto.request.ReadMessageUpdateRequestDto;
import chattingserver.dto.request.RoomCreateRequestDto;
import chattingserver.dto.request.UserEntranceRequestDto;
import chattingserver.dto.response.CommonAPIMessage;
import chattingserver.dto.response.JoinedRoomResponseDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.service.ChatMessageService;
import chattingserver.service.RoomService;
import chattingserver.service.SearchService;
import com.lalala.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/rooms")
@RequiredArgsConstructor
@Tag(name = "room", description = "채팅방 API")
public class RoomController {
    private final RoomService roomService;
    private final Producers producers;

    @PostMapping("/create")
    @Operation(summary = "채팅방 생성 API", description = "신규 채팅방 생성")
    public ResponseEntity<BaseResponse<RoomResponseDto>> createRoom(@Valid @RequestBody RoomCreateRequestDto request) {
        RoomResponseDto response = roomService.create(request);
        producers.sendRoomMessage(createIndexingRequestMessage(response));
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.CREATED.value(), "채팅방 생성 성공", response));
    }

    @PostMapping("/exit/{roomId}")
    @Operation(summary = "채팅방 영구적으로 나가기", description = "그룹 채팅방에서 유저 삭제")
    public ResponseEntity<BaseResponse<HashMap<String, String>>> exitRoom(@PathVariable String roomId, @RequestParam Long uid) {
        if (roomService.exitRoom(roomId, uid)) {
            HashMap<String, String> result = new HashMap<>();
            result.put("roomId", roomId);
            return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 영구 퇴장 성공", result));
        }
        throw new BusinessException("채팅방 나가기 실패", ErrorCode.ROOM_EXIT_FAILED);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "채팅방 정보 조회 API", description = "특정 채팅방 정보 조회")
    public ResponseEntity<BaseResponse<RoomResponseDto>> getRoomInfo(@PathVariable String roomId) {
        RoomResponseDto roomInfo = roomService.getRoomInfo(roomId);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 정보 조회 성공", roomInfo));
    }

    @GetMapping("/")
    @Operation(summary = "모든 채팅방 정보 조회 API", description = "모든 채팅방 정보 조회")
    public ResponseEntity<BaseResponse<List<RoomResponseDto>>> getAllRooms() {
        List<RoomResponseDto> allRoomInfos = roomService.getAllRoomInfos();
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "모든 채팅방 정보 조회 성공", allRoomInfos));
    }

    @GetMapping("/joined")
    @Operation(summary = "참여중인 채팅방 리스트 조회", description = "특정 유저가 참여중인 채팅방 리스트 조회")
    public ResponseEntity<BaseResponse<List<JoinedRoomResponseDto>>> getJoinedRooms(@RequestParam Long uid) {
        List<JoinedRoomResponseDto> joinedRooms = roomService.findJoinedRoomsByUid(uid);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "참여중인 채팅방 리스트 조회 성공", joinedRooms));
    }

    @GetMapping("/unjoined")
    @Operation(summary = "참여 가능한 채팅방 리스트 조회", description = "특정 유저가 참여할 수 있는 채팅방 리스트 조회")
    public ResponseEntity<BaseResponse<List<RoomResponseDto>>> getUnjoinedRooms(@RequestParam Long uid) {
        List<RoomResponseDto> unjoinedRooms = roomService.findUnjoinedRooms(uid);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "참여 가능한 채팅방 리스트 조회 성공", unjoinedRooms));
    }

    @PutMapping("/leave")
    @Operation(summary = "채팅방 잠시 나가기, 마지막 읽은 메시지 id 저장", description = "잠시 나가기 (완전히 나가기 아님)")
    public ResponseEntity<BaseResponse<CommonAPIMessage>> updateLastReadMsgId(@RequestBody ReadMessageUpdateRequestDto request) {
        CommonAPIMessage result = roomService.updateLastReadMsgId(request);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 잠시 나가기, 마지막 읽은 메시지 id 저장 성공", result));
    }

    private IndexingRequestMessageDto createIndexingRequestMessage(RoomResponseDto room) {
        return IndexingRequestMessageDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .playlistId(room.getPlaylist().getId())
                .thumbnailImage(room.getThumbnailImage())
                .build();
    }
}