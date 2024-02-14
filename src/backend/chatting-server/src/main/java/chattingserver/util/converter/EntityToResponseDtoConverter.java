package chattingserver.util.converter;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.room.Room;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.dto.response.RoomResponseDto;

public class EntityToResponseDtoConverter {

    public RoomResponseDto convertRoom(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .userCount(room.getUsers().size())
                .users(room.getUsers())
                .playlist(room.getPlaylist())
                .thumbnailImage(room.getThumbnailImage()) // 플레이리스트에 곡 무조건 존재함을 신뢰
                .build();
    }

    public ChatMessageDto convertMessage(ChatMessage message) {
        return ChatMessageDto.builder()
                .id(message.getId())
                .messageType(message.getMessageType())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .nickName(message.getNickName())
                .senderProfileImage(message.getSenderProfileImage())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public ChatMessageResponseDto convertToResponseMessage(ChatMessage message) {
        return ChatMessageResponseDto.builder()
                .id(message.getId())
                .messageType(message.getMessageType())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .nickName(message.getNickName())
                .senderProfileImage(message.getSenderProfileImage())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
