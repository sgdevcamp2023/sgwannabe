FROM openjdk:17-jdk-slim
CMD ["./gradlew", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
ARG DOCKER_YML_FILE=src/main/resources/application.yml
COPY ${JAR_FILE_PATH} app.jar
COPY ${DOCKER_YML_FILE} application.yml
ENTRYPOINT ["java", "-jar", "app.jar"]
