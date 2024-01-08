package spring.auth;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.auth.domain.ERole;
import spring.auth.domain.User;
import spring.auth.dto.request.SignUpRequest;
import spring.auth.repository.UserRepository;

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

//        private final EntityManager em;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

//        public void dbInitAdmin() {
//            extracted("a", "a", "관리자", Role.ADMIN);
//        }

        public void dbInitUsers() {
            extracted("신짱구", "jjangu@gmail.com", "aaaaaaaaaa");
            extracted("신짱아", "jjanga@gmail.com", "aaaaaaaaaa");
            extracted("봉미선", "misun@gmail.com", "aaaaaaaaaa");
            extracted("신형만", "man@gmail.com", "aaaaaaaaaa");
            extracted("흰둥이", "doong@gmail.com", "aaaaaaaaaa");
        }

        private void extracted(String username, String email, String password) {
            String hashPassword = passwordEncoder.encode(password);
            User user = new User(username, email, hashPassword);
            user.changeUserRole(ERole.ROLE_USER);
            userRepository.save(user);

        }



    }
}