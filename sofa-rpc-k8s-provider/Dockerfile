# Use the official OpenJDK base image for Java runtime
FROM openjdk:8-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot executable JAR file from the local target folder to the container
COPY ./appboot/target/appboot-0.0.1-SNAPSHOT.jar /app/

# The command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "appboot-0.0.1-SNAPSHOT.jar"]
