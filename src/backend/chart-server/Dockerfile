FROM openjdk:21-slim
ARG JAR_FILE=music-api/build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
