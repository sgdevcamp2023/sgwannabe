package userserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import userserver.domain.Role;
import userserver.domain.Status;
import userserver.domain.User;
import userserver.payload.response.ProfileTestResponse;
import userserver.repository.UserRepository;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void json_데이터_저장() throws Exception{
        //given
        String str = "{\"profile\":[{\"url\":\"http\", \"updated_at\":\"2024.01\"}, {\"url\":\"https\", \"updated_at\":\"2024.02\"}]}";
        String str_update = "2024.01";

        ObjectMapper mapper = new ObjectMapper();

        ProfileTestResponse dto = mapper.readValue(str, ProfileTestResponse.class);

        User user = User.builder()
                        .email("email")
                        .password("asd")
                        .nickname("신짱구")
                        .role(Role.ROLE_USER)
                        .status(Status.ENABLE)
                        .profile(dto.profile())
                        .build();

        userRepository.save(user);

        //then
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        JSONObject jsonObject = new JSONObject(findUser.getProfile().get(0));

        Assertions.assertThat(str_update).isEqualTo((String)jsonObject.get("updated_at"));

    }
}