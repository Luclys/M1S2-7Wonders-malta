FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-game-1.jar
COPY ${JAR_FILE} java-game.jar
ENTRYPOINT java -jar /java-game.jar