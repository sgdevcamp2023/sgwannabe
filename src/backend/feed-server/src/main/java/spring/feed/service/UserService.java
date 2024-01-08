package spring.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.feed.domain.Friendship;
import spring.feed.domain.User;
import spring.feed.exception.UserAlreadyExistsException;
import spring.feed.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public User addUser(User user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            String message = String.format("{}는 이미 존재합니다.", user.getUsername());
            throw new UserAlreadyExistsException(message);
        }

        User savedUser = userRepository.save(user);
        log.info("{} 저장 완료", savedUser.getUsername());
        return savedUser;
    }

    @Transactional
    public User startFollowing(User fromUser, User toUser) {
        User savedFromUser = userRepository.findByUserId(fromUser.getUserId())
                .orElseGet(() -> this.addUser(fromUser));
        User savedToUser = userRepository.findByUserId(toUser.getUserId())
                .orElseGet(() -> this.addUser(toUser));


        if (savedFromUser.getFriendships() == null) {
            log.info("저장된 유저의 friendship 없음");
            savedFromUser.setFriendships(new HashSet<>());
        }


        savedFromUser.getFriendships().add(Friendship.
                builder().
                startNode(savedFromUser).
                endNode(savedToUser).
                build());
        log.info("노드 연결 완료");

        return userRepository.save(fromUser);
    }

    public List<User> findFollowing(Long userId){
        List<User> following = userRepository.findFollowing(userId);
        log.info("{} 가 팔로잉 하는 사람들 {}", userId, following);

        return following;
    }
}
