package chattingserver.controller;

import chattingserver.config.kafka.Producers;
import chattingserver.domain.room.User;
import chattingserver.dto.RoomMessageDto;
import chattingserver.dto.request.RoomCreateRequestDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.service.RoomService;
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

import java.util.stream.Collectors;

@Tag(name = "room", description = "채팅방 API")
@Slf4j
@RestController
@RequestMapping("/v1/api/chatrooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final Producers producers;

    @Operation(summary = "채팅방 생성 API", description = "신규 채팅방 생성", responses = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
    @PostMapping("/create")
    public ResponseEntity<RoomResponseDto> groupCreation(@Valid @RequestBody RoomCreateRequestDto roomCreateRequestDto) {

        // 채팅방 C
        RoomResponseDto roomResponseDto = roomService.create(roomCreateRequestDto);

        // publish
        producers.sendRoomMessage(RoomMessageDto.builder()
                .receivers(roomResponseDto.getUsers().stream().map(User::getUid).collect(Collectors.toList()))
                .roomResponseDto(roomResponseDto)
                .build());

        return new ResponseEntity<>(roomResponseDto, HttpStatus.CREATED);
    }

//    @Operation(summary = "채팅방 정보 조회 API", description = "특정 채팅방 정보 조회", responses = {
//            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = RoomResponseDto.class)))})
//    @GetMapping("/{roomId}")
//    public ResponseEntity<RoomResponseDto> chatRoomInfo(@PathVariable(value = "roomId") String roomId) {
//        return new ResponseEntity<>(chatRoomMongoService.getChatRoomInfo(roomId), HttpStatus.OK);
//    }

}