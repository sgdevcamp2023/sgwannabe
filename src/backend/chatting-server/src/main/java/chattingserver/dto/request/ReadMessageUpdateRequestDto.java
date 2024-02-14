package chattingserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadMessageUpdateRequestDto {
    @NotBlank
    private String roomId;
    @NotBlank
    private Long uid;
    @NotBlank
    private String messageId;
}