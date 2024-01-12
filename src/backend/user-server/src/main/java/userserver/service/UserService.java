package userserver.service;

import org.springframework.http.ResponseEntity;
import userserver.payload.request.EmailAuthCodeRequest;

public interface UserService {
    void sendAuthCodeByEmail(EmailAuthCodeRequest request);
}
