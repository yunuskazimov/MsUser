FROM openjdk:16-slim-buster

COPY build/libs/MsUser-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar MsUser-0.0.1-SNAPSHOT.jar