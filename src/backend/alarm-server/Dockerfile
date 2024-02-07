# Build - https://findstar.pe.kr/2022/05/13/gradle-docker-cache/
FROM gradle:8.6.0-jdk17 as builder
WORKDIR /build

# 그레이들 파일이 변경되었을 때만 새로운 의존성 다운로드
COPY build.gradle settings.gradle /build/
COPY alarm-api/build.gradle /build/alarm-api/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

# Run
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/alarm-api/build/libs/*.jar app.jar

ARG DOCKER_YML_FILE=src/main/resources/application.yml
COPY ${DOCKER_YML_FILE} application.yml

EXPOSE 28000

ENTRYPOINT ["java", "-jar", "app.jar"]
