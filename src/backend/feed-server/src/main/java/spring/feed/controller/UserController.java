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
public class UserController {
    private final UserService userService;

    /**
     * fromUser가 toUser에게 팔로잉 요청
     */
    @PostMapping("/users/start-following")
    public ResponseEntity<?> startFollowing(@RequestBody FollowRequest request) {
        log.info("{}가 {}를 팔로우하기 시작했습니다.", request.fromUser().username(), request.toUser().username());

        userService.startFollowing(
                User.builder()
                        .userId(request.fromUser().userId())
                        .username(request.fromUser().username())
                        .email(request.fromUser().email())
                        .build(),
                User.builder()
                        .userId(request.toUser().userId())
                        .username(request.toUser().username())
                        .email(request.toUser().email())
                        .build()

        );
        String message = String.format("{} 가 {}를 팔로잉 했습니다", request.fromUser().username(), request.toUser().username());

        return ResponseEntity.ok(new ApiResponse(true, message));
    }


    /**
     * 특정 유저의 팔로잉 목록 조회
     */
    @GetMapping("/users/{userId}/following")
    public ResponseEntity<?> findFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findFollowing(userId));
    }
}
