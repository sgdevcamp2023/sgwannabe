FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/streaming-0.0.1-SNAPSHOT.jar
ARG DOCKER_YML_FILE=build/resources/main/application.yml
ARG FFMPEG_PATH=libs/ffmpeg-6.1-linux/

ADD ${JAR_FILE} streaming.jar
ADD ${DOCKER_YML_FILE} application.yml
ADD ${FFMPEG_PATH} libs/ffmpeg-6.1/

ENTRYPOINT ["java", "-jar", "/streaming.jar"]
