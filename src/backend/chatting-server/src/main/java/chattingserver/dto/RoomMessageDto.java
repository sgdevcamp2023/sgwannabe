package chattingserver.dto;

import java.util.List;

import lombok.*;

import chattingserver.dto.response.RoomResponseDto;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageDto {
    private List<Long> receivers;
    private RoomResponseDto roomResponseDto;
}
