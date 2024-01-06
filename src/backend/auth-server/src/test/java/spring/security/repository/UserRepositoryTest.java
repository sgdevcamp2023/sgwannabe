package spring.security.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.auth.domain.User;
import spring.auth.repository.UserRepository;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Test
    void findByUserName() throws Exception{
        //given
        User user = new User("신짱구", "a@gmail.com", "a");
        userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        //then
        Assertions.assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }
}