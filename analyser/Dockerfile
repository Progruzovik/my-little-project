FROM openjdk:11-jre-slim
WORKDIR /
ADD alfabattle-analyser.jar alfabattle-analyser.jar
ADD application.yml application.yml
ENTRYPOINT java -jar alfabattle-analyser.jar

EXPOSE 8081
