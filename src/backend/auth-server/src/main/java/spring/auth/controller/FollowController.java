package spring.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.auth.domain.FollowUser;
import spring.auth.service.FollowService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/follow")
@Tag(name="Follow", description="Follow API")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/find-one")
    public FollowUser findOneByEmail(@RequestParam("email") String email) {
        return followService.findOneByEmail(email);
    }
}