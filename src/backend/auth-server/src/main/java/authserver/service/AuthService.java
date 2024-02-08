package authserver.service;

import org.springframework.http.ResponseEntity;

import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request);

    ResponseEntity<?> signOut(HttpServletRequest request);
}
