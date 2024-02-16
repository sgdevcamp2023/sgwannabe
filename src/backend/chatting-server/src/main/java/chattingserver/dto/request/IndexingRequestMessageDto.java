package chattingserver.dto.request;

import lombok.*;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IndexingRequestMessageDto {
    private String roomId;
    private String roomName;
    private String playlistId;
    private String thumbnailImage;
}