package authserver.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import authserver.service.AuthService;
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
}
