# as required, use jdk17
FROM openjdk:17-jdk-slim

# copy jar
COPY target/incident-manager-backend-1.0.0.jar app.jar

# when container start ,run following
ENTRYPOINT ["java", "-jar", "/app.jar"]

# set running port
EXPOSE 8080