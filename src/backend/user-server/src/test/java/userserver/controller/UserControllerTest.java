package userserver.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import userserver.domain.Role;
import userserver.domain.Status;
import userserver.domain.User;
import userserver.repository.UserRepository;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void 프로필_데이터_저장() throws Exception{
        //given
        String profileURL = "https://www.lalala.com/image.png";

        User user = User.builder()
                        .email("email")
                        .password("asd")
                        .nickname("신짱구")
                        .role(Role.USER)
                        .status(Status.ACTIVE)
                        .profile(profileURL)
                        .build();

        userRepository.save(user);

        //then
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        Assertions.assertThat(findUser.getProfile()).isEqualTo(profileURL);

    }
}
