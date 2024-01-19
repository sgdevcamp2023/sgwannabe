FROM openjdk:17-jdk-slim
CMD ["./gradlew", "build"]
ARG JAR_FILE_PATH=music-api/build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
