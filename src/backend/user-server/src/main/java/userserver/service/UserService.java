package userserver.service;

import org.springframework.http.ResponseEntity;

import userserver.domain.User;
import userserver.payload.request.*;

public interface UserService {
    ResponseEntity<?> sendAuthCodeByEmail(EmailAuthCodeRequest request);

    ResponseEntity<?> verifyAuthCode(EmailVerifyRequest request);

    ResponseEntity<?> signUp(SignUpRequest request);

    ResponseEntity<?> passwordChange(User user, PasswordChangeRequest request);

    ResponseEntity<?> profileChange(User user, ProfileChangeRequest request);
}
