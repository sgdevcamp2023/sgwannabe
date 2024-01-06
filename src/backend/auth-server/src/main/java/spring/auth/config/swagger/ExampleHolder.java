package spring.auth.config.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;

@Builder
public record ExampleHolder(

        Example holder,
        String name,
        int code
) {
}
