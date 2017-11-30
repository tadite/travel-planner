FROM openjdk:8u111-jdk-alpine
EXPOSE 8080
ADD /target/travelplanner-0.0.1-SNAPSHOT.war travel_planner.war
ENTRYPOINT ["java", "-jar", "travel_planner.war"]
