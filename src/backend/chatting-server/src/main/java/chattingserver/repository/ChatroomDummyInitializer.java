package chattingserver.repository;

import chattingserver.domain.room.Music;
import chattingserver.domain.room.Playlist;
import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User dummyUser1 = User.builder()
                .uid(1L)
                .nickName("유저닉네임1")
                .enteredAt(LocalDateTime.now())
                .build();

        User dummyUser2 = User.builder()
                .uid(2L)
                .nickName("유저닉네임2")
                .enteredAt(LocalDateTime.now())
                .build();

        List<User> users = new ArrayList<>();
        users.add(dummyUser1);
        users.add(dummyUser2);

        Music dummyMusic1 = Music.builder()
                .title("음원1")
                .artist("아티스트1")
                .playtime("12:34")
                .thumbnail("thumbnail-1.url")
                .build();

        Music dummyMusic2 = Music.builder()
                .title("음원2")
                .artist("아티스트2")
                .playtime("01:23")
                .thumbnail("thumbnail-2.url")
                .build();

        List<Music> dummyMusics = new ArrayList<>();
        dummyMusics.add(dummyMusic1);
        dummyMusics.add(dummyMusic2);


        Playlist dummyPlaylist = Playlist.builder()
                .id("qwer1234")
                .name("플레이리스트1")
                .musics(dummyMusics)
                .build();

        // room build
        Room dummyRoom = Room.builder()
                .roomName(dummyPlaylist.getName())
                .playlist(dummyPlaylist)
                .users(users)
                .build();

        roomRepository.save(dummyRoom);
        log.info("방 생성 성공 room={}", dummyRoom);
    }
}
