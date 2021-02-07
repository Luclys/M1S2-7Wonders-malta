FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-player-1.jar
COPY ${JAR_FILE} java-player.jar
ENTRYPOINT java -jar /java-player.jar