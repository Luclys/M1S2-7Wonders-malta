FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-player-1.jar
COPY ${JAR_FILE} java-player.jar
ENTRYPOINT java  -Dserver.port=$PORT -jar /java-player.jar $ENGINE_IP $PORT $TEST