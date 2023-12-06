FROM openjdk:17-jre-slim

VOLUME /tmp

COPY target/cloud-learning.jar cloud-learning.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cloud-learning.jar"]