package authserver.service;

import authserver.payload.request.SignInRequest;
import authserver.payload.response.UserAndTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserAndTokenResponse> signIn(SignInRequest request);

    ResponseEntity<?> signOut(HttpServletRequest request);
}
