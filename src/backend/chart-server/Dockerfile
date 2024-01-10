FROM openjdk:21-slim
CMD ["./gradlew", "build"]
ARG JAR_FILE_PATH=chart-api/build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
