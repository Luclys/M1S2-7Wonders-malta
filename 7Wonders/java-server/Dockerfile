FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-server-1-jar-with-dependencies.jar
COPY ${JAR_FILE} java-server.jar
ENTRYPOINT java -jar /java-server.jar