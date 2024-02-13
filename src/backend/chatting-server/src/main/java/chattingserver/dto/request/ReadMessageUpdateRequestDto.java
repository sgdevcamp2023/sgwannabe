package chattingserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadMessageUpdateRequestDto {
    @NotBlank private String roomId;
    @NotBlank private String uid;
    @NotBlank private String messageId;
}
