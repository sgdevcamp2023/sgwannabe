package authserver.controller;

<<<<<<< HEAD
import authserver.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

=======
import authserver.exception.CustomUserProvider;
>>>>>>> 2e2b46c (feat: exception handling)
import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<UserAndTokenResponse> singInUser(
            @Validated @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return authService.signOut(request);
    }

    @PostMapping("/passport")
    public ResponseEntity<String> generatePassport(@RequestHeader("Authorization") String jwtPayload) {
        return ResponseEntity.ok().body(authService.generatePassport(jwtPayload));
    }
}
