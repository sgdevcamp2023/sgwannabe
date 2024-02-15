package chattingserver.controller;

import chattingserver.config.kafka.Producers;
import chattingserver.domain.room.User;
import chattingserver.dto.RoomMessageDto;
import chattingserver.dto.request.IndexingRequestMessageDto;
import chattingserver.dto.request.ReadMessageUpdateRequestDto;
import chattingserver.dto.request.RoomCreateRequestDto;
import chattingserver.dto.request.UserEntranceRequestDto;
import chattingserver.dto.response.CommonAPIMessage;
import chattingserver.dto.response.JoinedRoomResponseDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.dto.response.UserListResponseDto;
import chattingserver.service.ChatMessageService;
import chattingserver.service.RoomService;
import chattingserver.service.SearchService;
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
import java.util.stream.Collectors;

@Tag(name = "room", description = "채팅방 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ChatMessageService chatMessageService;
    private final SearchService searchService;
    private final Producers producers;

    @Operation(summary = "채팅방 생성 API", description = "신규 채팅방 생성", responses = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @PostMapping("/create")
    public ResponseEntity<CommonAPIMessage> groupCreation(@Valid @RequestBody RoomCreateRequestDto roomCreateRequestDto) {


        RoomResponseDto roomResponseDto = roomService.create(roomCreateRequestDto);

        producers.sendRoomMessage(IndexingRequestMessageDto.builder()
                .roomId(roomResponseDto.getId())
                .roomName(roomResponseDto.getRoomName())
                .playlistId(roomResponseDto.getPlaylist().getId())
                .thumbnailImage(roomResponseDto.getThumbnailImage())
                .build());

        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(roomResponseDto);

        searchService.sendIndexingRequestToSearchServer(roomResponseDto);

        return new ResponseEntity<>(apiMessage, HttpStatus.CREATED);
    }

    @Operation(summary = "채팅방 영구적으로 나가기", description = "그룹 채팅방에서 유저 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @PostMapping("/exit/{roomId}")
    public ResponseEntity<CommonAPIMessage> outOfTheRoom(@PathVariable(value = "roomId") String roomId, @RequestBody UserEntranceRequestDto userDto) {
        producers.sendMessage(chatMessageService.permanentLeaving(roomId, userDto));
        if (roomService.exitRoom(roomId, userDto.getUid())) {
            return new ResponseEntity<>(new CommonAPIMessage(CommonAPIMessage.ResultEnum.success, new HashMap<String, String>() {{
                put("roomId", roomId);
            }}), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CommonAPIMessage(CommonAPIMessage.ResultEnum.failed, new HashMap<String, String>() {{
            log.error("exitRoom 채팅방 나가기 실패 roomId: {}, userId: {}", roomId, userDto.getUid());
            put("roomId", roomId);
        }}), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "채팅방 정보 조회 API", description = "특정 채팅방 정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @GetMapping("/{roomId}")
    public ResponseEntity<CommonAPIMessage> chatRoomInfo(@PathVariable(value = "roomId") String roomId) {
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(roomService.getRoomInfo(roomId));
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @Operation(summary = "모든 채팅방 정보 조회 API", description = "모든 채팅방 정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @GetMapping("/")
    public ResponseEntity<CommonAPIMessage> getAllChatRoomInfos() {
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(roomService.getAllRoomInfos());
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @Operation(summary = "참여중인 채팅방 리스트 조회", description = "특정 유저가 참여중인 채팅방 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/joined")
    public ResponseEntity<CommonAPIMessage> myChatRooms(@RequestParam(required = true) Long uid){
        List<JoinedRoomResponseDto> joinedRoomResponseDtos = roomService.findJoinedRoomsByUid(uid);
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(joinedRoomResponseDtos);
        return new ResponseEntity<>(apiMessage,HttpStatus.OK);
    }

    @Operation(summary = "참여 가능한 채팅방 리스트 조회", description = "특정 유저가 참여할 수 있는 채팅방 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonAPIMessage.class)))})
    @GetMapping("/unjoined")
    public ResponseEntity<CommonAPIMessage> unjoinedChatRooms(@RequestParam(required = true) Long uid) {
        List<RoomResponseDto> unjoinedRooms = roomService.findUnjoinedRooms(uid);
        CommonAPIMessage apiMessage = new CommonAPIMessage();
        apiMessage.setMessage(CommonAPIMessage.ResultEnum.success);
        apiMessage.setData(unjoinedRooms);
        return new ResponseEntity<>(apiMessage, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 잠시 나가기, 마지막 읽은 메시지 id 저장", description = "잠시 나가기 (완전히 나가기 아님)")
    @PutMapping("/leave")
    public ResponseEntity<CommonAPIMessage> updateLastReadMsgId(@RequestBody ReadMessageUpdateRequestDto readMessageUpdateRequestDto){
        return new ResponseEntity<>(roomService.updateLastReadMsgId(readMessageUpdateRequestDto), HttpStatus.OK);
    }

}