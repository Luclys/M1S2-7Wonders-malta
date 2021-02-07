FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/java-common-1.jar
COPY ${JAR_FILE} java-common.jar
ENTRYPOINT java -jar /java-common.jar