FROM openjdk:8u111-jdk-alpine
EXPOSE 8080
ADD /target/travelplanner-0.0.1-SNAPSHOT.war travel_planner.war

RUN curl -L https://github.com/openshift/origin/releases/download/v3.7.0/openshift-origin-client-tools-v3.7.0-7ed6862-linux-64bit.tar.gz | \
  tar zx && mv openshift-origin-client-tools-v3.7.0-7ed6862-linux-64bit/oc /usr/bin/ && \
  oc version
  
ENTRYPOINT ["java", "-jar", "travel_planner.war"]