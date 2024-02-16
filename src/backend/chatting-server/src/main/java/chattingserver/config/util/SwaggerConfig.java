package chattingserver.config.util;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

@OpenAPIDefinition(
        info = @Info(title = "Chatting-Server", description = "chatting server api명세", version = "V1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/v1/**", "/**/chat"};

        return GroupedOpenApi.builder().group("CHATTING-SERVER API V1").pathsToMatch(paths).build();
    }
}
