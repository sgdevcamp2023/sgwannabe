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

@Tag(name = "room", description = "채팅방 API")
@Slf4j
@RestController
@RequestMapping("/v1/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ChatMessageService chatMessageService;
    private final SearchService searchService;
    private final Producers producers;

    @Operation(summary = "채팅방 생성 API", description = "신규 채팅방 생성", responses = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<RoomResponseDto>> groupCreation(@Valid @RequestBody RoomCreateRequestDto roomCreateRequestDto) {


        RoomResponseDto roomResponseDto = roomService.create(roomCreateRequestDto);

        producers.sendRoomMessage(IndexingRequestMessageDto.builder()
                .roomId(roomResponseDto.getId())
                .roomName(roomResponseDto.getRoomName())
                .playlistId(roomResponseDto.getPlaylist().getId())
                .thumbnailImage(roomResponseDto.getThumbnailImage())
                .build());

        log.info("roomRespDto-Controller-roomResponseDto={}", roomResponseDto);

        searchService.sendIndexingRequestToSearchServer(roomResponseDto);

        return ResponseEntity.ok(BaseResponse.from(HttpStatus.CREATED.value(), "채팅방 생성 성공", roomResponseDto));
    }

    @Operation(summary = "채팅방 영구적으로 나가기", description = "그룹 채팅방에서 유저 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @PostMapping("/exit/{roomId}")
    public ResponseEntity<BaseResponse<HashMap<String, String>>> outOfTheRoom(@PathVariable(value = "roomId") String roomId, @RequestParam Long uid) {
        if (roomService.exitRoom(roomId, uid)) {

            HashMap<String, String> roomId1 = new HashMap<>();
            roomId1.put("roomId", roomId);

            return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 영구 퇴장 성공", roomId1));
        }
        log.error("exitRoom 채팅방 나가기 실패 roomId: {}, userId: {}", roomId, uid);
        return null; // TODO common err 처리
    }

    @Operation(summary = "채팅방 정보 조회 API", description = "특정 채팅방 정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @GetMapping("/{roomId}")
    public ResponseEntity<BaseResponse<RoomResponseDto>> chatRoomInfo(@PathVariable(value = "roomId") String roomId) {
        RoomResponseDto roomInfo = roomService.getRoomInfo(roomId);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 정보 조회 성공", roomInfo));
    }

    @Operation(summary = "모든 채팅방 정보 조회 API", description = "모든 채팅방 정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<RoomResponseDto>>> getAllChatRoomInfos() {
        List<RoomResponseDto> allRoomInfos = roomService.getAllRoomInfos();
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "모든 채팅방 정보 조회 성공", allRoomInfos));
    }

    @Operation(summary = "참여중인 채팅방 리스트 조회", description = "특정 유저가 참여중인 채팅방 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/joined")
    public ResponseEntity<BaseResponse<List<JoinedRoomResponseDto>>> myChatRooms(@RequestParam(required = true) Long uid) {
        List<JoinedRoomResponseDto> joinedRoomResponseDtos = roomService.findJoinedRoomsByUid(uid);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "참여중인 채팅방 리스트 조회 성공", joinedRoomResponseDtos));
    }

    @Operation(summary = "참여 가능한 채팅방 리스트 조회", description = "특정 유저가 참여할 수 있는 채팅방 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/unjoined")
    public ResponseEntity<BaseResponse<List<RoomResponseDto>>> unjoinedChatRooms(@RequestParam(required = true) Long uid) {
        List<RoomResponseDto> unjoinedRooms = roomService.findUnjoinedRooms(uid);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "참여 가능한 채팅방 리스트 조회 성공", unjoinedRooms));
    }

    @Operation(summary = "채팅방 잠시 나가기, 마지막 읽은 메시지 id 저장", description = "잠시 나가기 (완전히 나가기 아님)")
    @PutMapping("/leave")
    public ResponseEntity<BaseResponse<CommonAPIMessage>> updateLastReadMsgId(@RequestBody ReadMessageUpdateRequestDto readMessageUpdateRequestDto) {
        CommonAPIMessage commonAPIMessage = roomService.updateLastReadMsgId(readMessageUpdateRequestDto);
        return ResponseEntity.ok(BaseResponse.from(HttpStatus.OK.value(), "채팅방 잠시 나가기, 마지막 읽은 메시지 id 저장 성공", commonAPIMessage));
    }

}