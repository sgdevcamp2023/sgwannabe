package spring.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import spring.auth.domain.User;
import spring.auth.dto.request.ChangePasswordRequest;
import spring.auth.dto.request.SignInRequest;
import spring.auth.dto.request.SignUpRequest;
import spring.auth.dto.response.UserAndTokenResponse;
import spring.auth.dto.response.UserInfoResponse;

public interface UserService {
    ResponseEntity<UserInfoResponse> signUp(SignUpRequest signUpRequest);

    ResponseEntity<UserAndTokenResponse> signIn(SignInRequest signInRequest);

    ResponseEntity<?> signOut(HttpServletRequest request);

    ResponseEntity<UserAndTokenResponse> refreshToken(HttpServletRequest request);
    String hashPassword(String password);

    ResponseEntity<?> changePassword(User user, ChangePasswordRequest request);

    ResponseEntity<?> uploadProfile(User user, String upload);

    ResponseEntity<?> deleteProfile(User user);


}
