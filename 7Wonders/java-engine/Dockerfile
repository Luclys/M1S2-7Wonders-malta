FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-engine-1.jar
COPY ${JAR_FILE} java-engine.jar
ENTRYPOINT java -jar /java-engine.jar