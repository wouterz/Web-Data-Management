FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY . app
EXPOSE 8761


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/build/libs/eureka-server.jar"]