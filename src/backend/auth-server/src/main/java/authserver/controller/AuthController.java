package authserver.controller;

import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import authserver.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth-service")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<UserAndTokenResponse> singInUser(@Validated @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return authService.signOut(request);
    }

}
