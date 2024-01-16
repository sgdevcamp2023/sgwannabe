package chattingserver.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class ChatRoom {

    private String id;
    private String roomName;
    private long userCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private HashMap<String, String> userList = new HashMap<>();

    public ChatRoom create(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        return chatRoom;
    }
}
