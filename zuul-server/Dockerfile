FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY . app
EXPOSE 8762


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/build/libs/zuul-server.jar"]