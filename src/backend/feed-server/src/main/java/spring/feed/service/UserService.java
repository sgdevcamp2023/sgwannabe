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

    @Transactional
    public User addUser(User user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            String message = String.format("%s는 이미 존재합니다.", user.getNickname());
            throw new UserAlreadyExistsException(message);
        }

        User savedUser = userRepository.save(user);
        log.info("{} 저장 완료", savedUser.getNickname());
        return savedUser;
    }

    @Transactional
    public void startFollowing(User fromUser, User toUser) {
        User savedFromUser = userRepository
                .findByUserId(fromUser.getUserId())
                .orElseGet(() -> {
                    log.info("{} 없음", fromUser.getNickname());
                    return this.addUser(fromUser);});

        User savedToUser = userRepository
                .findByUserId(toUser.getUserId())
                .orElseGet(() -> {
                    log.info("{} 없음", toUser.getNickname());
                    return this.addUser(toUser);});

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

        userRepository.save(savedFromUser);
    }

    public List<User> findFollowing(String userId) {
        List<User> following = userRepository.findFollowing(userId);
        log.info("{} 가 팔로잉 하는 사람들 {}", userId, following);

        return following;
    }

        public boolean isFollowing(String fromUserId, String toUserId) {
        return userRepository.isFollowing(fromUserId, toUserId);
    }

    public List<User> findFollowers(String userId) {
        return userRepository.findFollowers(userId);
    }


    @Transactional
    public void stopFollowing(String fromUserId, String toUserId) {
        userRepository.stopFollowing(fromUserId, toUserId);
    }
}
