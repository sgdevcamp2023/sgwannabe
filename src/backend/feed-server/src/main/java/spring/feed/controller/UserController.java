package spring.feed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.feed.domain.User;
import spring.feed.dto.request.FollowRequest;
import spring.feed.dto.response.ApiResponse;
import spring.feed.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class UserController {
    private final UserService userService;

    /**
     * fromUser가 toUser에게 팔로잉 요청
     */
    @PostMapping("/following")
    public ResponseEntity<?> startFollowing(@RequestBody FollowRequest request) {
        log.info("{}가 {}를 팔로우하기 시작했습니다.", request.fromUser().nickname(), request.toUser().nickname());

        userService.startFollowing(
                User.builder()
                        .userId(request.fromUser().userId())
                        .nickname(request.fromUser().nickname())
                        .email(request.fromUser().email())
                        .build(),
                User.builder()
                        .userId(request.toUser().userId())
                        .nickname(request.toUser().nickname())
                        .email(request.toUser().email())
                        .build()

        );
        String message = String.format("%s 가 %s를 팔로잉 했습니다", request.fromUser().nickname(), request.toUser().nickname());

        return ResponseEntity.ok(new ApiResponse(true, message));
    }


    /**
     * 특정 유저의 팔로잉 목록 조회
     */
    @GetMapping("/{userId}/following")
    public ResponseEntity<?> findFollowing(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findFollowing(userId));
    }

    /**
     * A가 B를 팔로잉하고 있는지 확인
     */
    @GetMapping("/{fromUserId}/following/{toUserId}")
    public ResponseEntity<?> isFollowing(@PathVariable String fromUserId, @PathVariable String toUserId) {
        return ResponseEntity.ok(userService.isFollowing(fromUserId, toUserId));
    }

    /**
     * A가 B 팔로잉 취소
     */
    @PostMapping("/{fromUserId}/following/{toUserId}")
    public ResponseEntity<?> endFollowing(@PathVariable String fromUserId, @PathVariable String toUserId) {
        userService.stopFollowing(fromUserId, toUserId);
        String message = String.format("%s 가 %s의 팔로잉을 취소 했습니다", fromUserId, toUserId);
        return ResponseEntity.ok(new ApiResponse(true, message));
    }

    /**
     * A의 팔로워 조회
     */
    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> findFollowers(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findFollowers(userId));
    }

}