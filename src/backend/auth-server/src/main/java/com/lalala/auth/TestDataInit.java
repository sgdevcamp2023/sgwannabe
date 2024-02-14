package com.lalala.auth;

import com.lalala.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lalala.auth.domain.Role;
import com.lalala.auth.domain.Status;
import com.lalala.auth.domain.User;
import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInitUsers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final AuthRepository authRepository;
        private final PasswordEncoder passwordEncoder;

        public void dbInitUsers() {
            extracted("신짱구", "jjangu@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
            extracted("신짱아", "jjanga@gmail.com", "aaaaaaaaaa", Status.BLOCK);
            extracted("봉미선", "misun@gmail.com", "aaaaaaaaaa", Status.DELETE);
            extracted("신형만", "man@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
            extracted("흰둥이", "doong@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
        }

        private void extracted(String nickname, String email, String password, Status status) {
            if (authRepository.findByEmail(email).isPresent()) {
                return;
            }

            String hashPassword = passwordEncoder.encode(password);
            User user = new User(nickname, email, hashPassword, status);
            user.changeUserRole(Role.USER);
            authRepository.save(user);
        }
    }
}
