package chattingserver.util.converter;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.dto.response.UserListResponseDto;

import java.util.stream.Collectors;

public class EntityToResponseDtoConverter {

    public RoomResponseDto convertRoom(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .playlistOwner(convertUser(room.getPlaylistOwner()))
                .userCount(room.getUsers().size())
                .users(room.getUsers().stream().map(this::convertUser).collect(Collectors.toList()))
                .musicCount(room.getPlaylist().getMusics().size())
                .playlist(room.getPlaylist())
                .createdAt(room.getCreatedAt())
                .thumbnailImage(room.getThumbnailImage()) // 플레이리스트에 곡 무조건 존재함을 신뢰
                .build();
    }

    public UserListResponseDto convertUser(User user) {
        return UserListResponseDto.builder()
                .uid(user.getUid())
                .nickName(user.getNickName())
                .profileImage(user.getProfileImage())
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
