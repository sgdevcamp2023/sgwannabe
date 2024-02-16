FROM openjdk:17-jdk-slim
ARG FFMPEG_PATH=libs/ffmpeg-6.1-linux/
ADD ${FFMPEG_PATH} libs/ffmpeg-6.1/
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
