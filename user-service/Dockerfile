FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY . app
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/build/libs/user-service.jar"]