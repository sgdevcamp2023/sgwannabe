package chattingserver.controller;

import chattingserver.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/messages")
    public void message(ChatMessageDto message) {
        messagingTemplate.convertAndSend("/sub/rooms/" + message.getRoomId(), message);
    }
}
