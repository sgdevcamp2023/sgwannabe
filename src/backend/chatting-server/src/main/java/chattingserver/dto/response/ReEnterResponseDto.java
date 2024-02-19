package chattingserver.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReEnterResponseDto {
    private List<ChatMessageResponseDto> beforeMessages;
    private List<ChatMessageResponseDto> newMessages;
    private Long currentMusicId;
}
