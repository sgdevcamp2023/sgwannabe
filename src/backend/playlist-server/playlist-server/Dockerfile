FROM openjdk:17-jdk-slim

COPY build/libs/*.jar /app.jar

WORKDIR /app

CMD ["java", "-jar", "playlist-server.jar"]