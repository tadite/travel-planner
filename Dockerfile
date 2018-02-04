FROM openjdk:8u111-jdk-alpine
ADD /target/travelplanner-0.0.1-SNAPSHOT.jar app.jar
COPY /json json
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.name=deploy"]