package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.room.Music;
import chattingserver.domain.room.Playlist;
import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.util.constant.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ChatroomDummyInitializer implements ApplicationRunner {

    private final RoomRepository roomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        User dummyUser1 = User.builder()
//                .uid(1L)
//                .nickName("유저닉네임1")
//                .profileImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png")
//                .enteredAt(LocalDateTime.now())
//                .build();
//
//        User dummyUser2 = User.builder()
//                .uid(2L)
//                .nickName("유저닉네임2")
//                .profileImage("")
//                .enteredAt(LocalDateTime.now())
//                .build();
//
//        List<User> users = new ArrayList<>();
//        users.add(dummyUser1);
//        users.add(dummyUser2);
//
//        Music dummyMusic1 = Music.builder()
//                .id(1L)
//                .title("음원1")
//                .artist("아티스트1")
//                .playtime("12:34")
//                .thumbnail("https://marketplace.canva.com/EAFHtE880QY/1/0/1600w/canva-blue-and-red-modern-music-youtube-thumbnail-cBAYqTj4TLk.jpg")
//                .build();
//
//        Music dummyMusic2 = Music.builder()
//                .id(2L)
//                .title("음원2")
//                .artist("아티스트2")
//                .playtime("01:23")
//                .thumbnail("https://marketplace.canva.com/EAFOwKVP0Ck/1/0/1600w/canva-blue-orange-colorful-aesthetic-minimalist-lofi-music-youtube-thumbnail-ETT-krmERlk.jpg")
//                .build();
//
//        List<Music> dummyMusics = new ArrayList<>();
//        dummyMusics.add(dummyMusic1);
//        dummyMusics.add(dummyMusic2);
//
//
//        Playlist dummyPlaylist = Playlist.builder()
//                .id("qwer1234")
//                .name("플레이리스트1")
//                .musics(dummyMusics)
//                .playlistOwnerId(dummyUser1.getUid())
//                .playlistOwnerNickName(dummyUser1.getNickName())
//                .playlistOwnerProfileImage(dummyUser1.getProfileImage())
//                .build();
//
//        // room build
//        Room dummyRoom = Room.builder()
//                .roomName(dummyPlaylist.getName())
//                .playlist(dummyPlaylist)
//                .playlistDuration(dummyPlaylist.getTotalPlaylistTime())
//                .thumbnailImage(dummyPlaylist.getFirstMusic().getThumbnail())
//                .users(users)
//                .playlistOwner(dummyUser1)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        roomRepository.save(dummyRoom);
//        log.info("방 생성 성공 room={}", dummyRoom);
//
//        ChatMessage message1 = chatMessageRepository.save(ChatMessage.builder()
//                .messageType(MessageType.MSG)
//                .roomId(dummyRoom.getId())
//                .senderId(dummyUser1.getUid())
//                .nickName(dummyUser1.getNickName())
//                .senderProfileImage(dummyUser1.getProfileImage())
//                .content("메시지메시지1")
//                .createdAt(LocalDateTime.now())
//                .build());
//
//        ChatMessage message2 = chatMessageRepository.save(ChatMessage.builder()
//                .messageType(MessageType.MSG)
//                .roomId(dummyRoom.getId())
//                .senderId(dummyUser2.getUid())
//                .nickName(dummyUser2.getNickName())
//                .senderProfileImage(dummyUser2.getProfileImage())
//                .content("메시지메시지2")
//                .createdAt(LocalDateTime.now())
//                .build());
//
//        log.info("메시지 생성 성공 message1={}", message1);
//        log.info("메시지 생성 성공 message2={}", message2);
//
//        User dummyUser3 = User.builder()
//                .uid(3L)
//                .nickName("유저닉네임3")
//                .profileImage("https://t4.ftcdn.net/jpg/03/64/21/11/360_F_364211147_1qgLVxv1Tcq0Ohz3FawUfrtONzz8nq3e.jpg")
//                .enteredAt(LocalDateTime.now())
//                .build();
//
//        User dummyUser4 = User.builder()
//                .uid(3L)
//                .nickName("유저닉네임4")
//                .profileImage("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?cs=srgb&dl=pexels-mohamed-abdelghaffar-771742.jpg&fm=jpg")
//                .enteredAt(LocalDateTime.now())
//                .build();
//
//        List<User> users2 = new ArrayList<>();
//        users2.add(dummyUser3);
//        users2.add(dummyUser4);
//        users2.add(dummyUser1);
//
//        Music dummyMusic3 = Music.builder()
//                .id(3L)
//                .title("음원3")
//                .artist("아티스트3")
//                .playtime("12:34")
//                .thumbnail("https://content.wepik.com/statics/13638236/preview-page0.jpg")
//                .build();
//
//        List<Music> dummyMusics2 = new ArrayList<>();
//        dummyMusics2.add(dummyMusic1);
//        dummyMusics2.add(dummyMusic3);
//
//
//        Playlist dummyPlaylist2 = Playlist.builder()
//                .id("098765432qwerty")
//                .name("플레이리스트2")
//                .musics(dummyMusics2)
//                .playlistOwnerId(dummyUser3.getUid())
//                .playlistOwnerNickName(dummyUser3.getNickName())
//                .playlistOwnerProfileImage(dummyUser3.getProfileImage())
//                .build();
//
//        log.info("dummyPlaylist2={}", dummyPlaylist2.toString());
//        log.info("dummyPlaylist2 firstMusic={}", dummyPlaylist2.getFirstMusic().toString());
//
//        // room build
//        Room dummyRoom2 = Room.builder()
//                .roomName(dummyPlaylist2.getName())
//                .playlist(dummyPlaylist2)
//                .playlistDuration(dummyPlaylist2.getTotalPlaylistTime())
//                .thumbnailImage(dummyPlaylist2.getFirstMusic().getThumbnail())
//                .users(users2)
//                .playlistOwner(dummyUser3)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        roomRepository.save(dummyRoom2);
//        log.info("방 생성 성공 room={}", dummyRoom);
//
//        ChatMessage message3 = chatMessageRepository.save(ChatMessage.builder()
//                .messageType(MessageType.MSG)
//                .roomId(dummyRoom2.getId())
//                .senderId(dummyUser1.getUid())
//                .nickName(dummyUser1.getNickName())
//                .senderProfileImage(dummyUser1.getProfileImage())
//                .content("메시지메시지3")
//                .createdAt(LocalDateTime.now())
//                .build());
//
//        ChatMessage message4 = chatMessageRepository.save(ChatMessage.builder()
//                .messageType(MessageType.MSG)
//                .roomId(dummyRoom2.getId())
//                .senderId(dummyUser4.getUid())
//                .nickName(dummyUser4.getNickName())
//                .senderProfileImage(dummyUser4.getProfileImage())
//                .content("메시지메시지4")
//                .createdAt(LocalDateTime.now())
//                .build());
//
//        log.info("메시지 생성 성공 message3={}", message3);
//        log.info("메시지 생성 성공 message4={}", message4);


    }
}
