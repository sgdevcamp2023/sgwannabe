package authserver;

import authserver.domain.Role;
import authserver.domain.Status;
import authserver.domain.User;
import authserver.repository.AuthRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInitAdmin();
        initService.dbInitUsers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final AuthRepository authRepository;
        private final PasswordEncoder passwordEncoder;

//        public void dbInitAdmin() {
//            extracted("a", "a", "관리자", Role.ADMIN);
//        }

        public void dbInitUsers() {
            extracted("신짱구", "jjangu@gmail.com", "aaaaaaaaaa", Status.DISABLE);
            extracted("신짱아", "jjanga@gmail.com", "aaaaaaaaaa", Status.ENABLE);
            extracted("봉미선", "misun@gmail.com", "aaaaaaaaaa", Status.QUIT);
            extracted("신형만", "man@gmail.com", "aaaaaaaaaa", Status.ENABLE);
            extracted("흰둥이", "doong@gmail.com", "aaaaaaaaaa", Status.ENABLE);
        }

        private void extracted(String nickname, String email, String password, Status status) {
            String hashPassword = passwordEncoder.encode(password);
            User user = new User(nickname, email, hashPassword, status);
            user.changeUserRole(Role.ROLE_USER);
            authRepository.save(user);

        }



    }
}
