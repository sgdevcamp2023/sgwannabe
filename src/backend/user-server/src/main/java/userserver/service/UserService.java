package userserver.service;

import org.springframework.http.ResponseEntity;
import userserver.payload.request.EmailAuthCodeRequest;
import userserver.payload.request.EmailVerifyRequest;
import userserver.payload.request.SignUpRequest;

public interface UserService {
    void sendAuthCodeByEmail(EmailAuthCodeRequest request);
    void verifyAuthCode(EmailVerifyRequest request);

    void signUp(SignUpRequest request);

}
