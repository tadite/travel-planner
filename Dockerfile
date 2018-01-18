FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/travelplanner-0.0.1-SNAPSHOT.jar app.jar
ADD /json json
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]