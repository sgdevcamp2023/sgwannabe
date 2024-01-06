package spring.auth.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import spring.auth.common.annotation.ApiErrorCode;
import spring.auth.common.annotation.ApiErrorException;
import spring.auth.common.annotation.ExplainError;
import spring.auth.common.exception.BaseErrorCode;
import spring.auth.common.exception.GlobalCustomException;
import spring.auth.common.exception.ErrorReason;
import spring.auth.common.exception.ErrorResponse;
import spring.auth.config.swagger.ExampleHolder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
        info= @Info(
                title="인증 서버 API",
                description="인증관리와 멤버십관리 기능을 제공합니다",
                version="v1"
        )
)
public class SwaggerConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            ApiErrorException apiErrorExceptionsExample =
                    handlerMethod.getMethodAnnotation(ApiErrorException.class);
            ApiErrorCode apiErrorCodeExample =
                    handlerMethod.getMethodAnnotation(ApiErrorCode.class);

            if (apiErrorExceptionsExample != null) {
                generateExceptionResponse(operation, apiErrorExceptionsExample.value());
            }

            if (apiErrorCodeExample != null) {
                generateErrorCodeResponse(operation, apiErrorCodeExample.value());
            }

            return operation;
        };
    }
    /**
     * BaseErrorCode 타입의 enum 문서화
     */
    private void generateErrorCodeResponse(Operation operation, Class<? extends BaseErrorCode> type) {
        ApiResponses responses = operation.getResponses();
        BaseErrorCode[] errorCodes = type.getEnumConstants();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
                .map(this::createExampleHolderForErrorCode)
                .collect(groupingBy(ExampleHolder::code));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private ExampleHolder createExampleHolderForErrorCode(BaseErrorCode baseErrorCode) {
        try {
            ErrorReason errorReason = baseErrorCode.getErrorReason();
            return ExampleHolder.builder()
                    .holder(getSwaggerExample(baseErrorCode.getExplainError(), errorReason))
                    .code(errorReason.status())
                    .name(errorReason.code())
                    .build();
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid field while accessing BaseErrorCode: " + e.getMessage(), e);
        }
    }
    private void generateExceptionResponse(Operation operation, Class<?> type) {
        ApiResponses responses = operation.getResponses();

        Object bean = applicationContext.getBean(type);

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(ExplainError.class) != null)
                .filter(field -> field.getType() == GlobalCustomException.class)
                .map(field -> createExampleHolderForException(field, bean))
                .collect(groupingBy(ExampleHolder::code));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }


    private ExampleHolder createExampleHolderForException(Field field, Object bean) {
        try {
            GlobalCustomException exception = (GlobalCustomException) field.get(bean);
            ExplainError annotation = field.getAnnotation(ExplainError.class);
            String value = annotation.value();
            ErrorReason errorReason = exception.getErrorReason();
            return ExampleHolder.builder()
                    .holder(getSwaggerExample(value, errorReason))
                    .code(errorReason.status())
                    .name(field.getName())
                    .build();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Example getSwaggerExample(String value, ErrorReason errorReason) {
        ErrorResponse errorResponse = new ErrorResponse(errorReason, "요청한 URL 정보 (ex.\"http://localhost:8080/api/auth/signup\")");
        Example example = new Example();
        example.description(value);
        example.setValue(errorResponse);
        return example;
    }

    private void addExamplesToResponses(
            ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    io.swagger.v3.oas.models.responses.ApiResponse apiResponse = new io.swagger.v3.oas.models.responses.ApiResponse();
                    v.forEach(
                            exampleHolder -> {
                                mediaType.addExamples(
                                        exampleHolder.name(), exampleHolder.holder());
                            });
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(status.toString(), apiResponse);
                });
    }

}
