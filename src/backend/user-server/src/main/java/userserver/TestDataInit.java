package userserver;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import userserver.domain.Role;
import userserver.domain.Status;
import userserver.domain.User;
import userserver.repository.UserRepository;

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

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void dbInitUsers() {
            extracted("신짱구", "jjangu@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
            extracted("신짱아", "jjanga@gmail.com", "aaaaaaaaaa", Status.BLOCK);
            extracted("봉미선", "misun@gmail.com", "aaaaaaaaaa", Status.DELETE);
            extracted("신형만", "man@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
            extracted("흰둥이", "doong@gmail.com", "aaaaaaaaaa", Status.ACTIVE);
        }

        private void extracted(String nickname, String email, String password, Status status) {
            if (userRepository.findByEmail(email).isPresent()) {
                return;
            }

            String hashPassword = passwordEncoder.encode(password);
            User user = new User(nickname, email, hashPassword, status);
            user.changeUserRole(Role.USER);
            userRepository.save(user);
        }
    }
}
