package chattingserver.dto;

import chattingserver.dto.response.RoomResponseDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageDto {
    private List<Long> receivers;
    private RoomResponseDto roomResponseDto;

}
