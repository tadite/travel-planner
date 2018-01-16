FROM openjdk:8u111-jdk-alpine
EXPOSE 8080
ADD /target/travelplanner-0.0.1-SNAPSHOT.war travel_planner.war

RUN curl -L https://github.com/openshift/origin/releases/download/v1.4.1/openshift-origin-client-tools-v1.4.1-3f9807a-linux-64bit.tar.gz | \
  tar zx && mv openshift-origin-client-tools-v1.4.1+3f9807a-linux-64bit/oc /usr/bin/ && \
  oc version
  
ENTRYPOINT ["java", "-jar", "travel_planner.war"]