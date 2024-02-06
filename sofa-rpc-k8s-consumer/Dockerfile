# Use the official OpenJDK base image for Java runtime
FROM openjdk:8-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot executable JAR file from the local target folder to the container
COPY ./target/sofa-rpc-k8s-consumer-0.0.1-SNAPSHOT.jar /app/
EXPOSE 5005
# The command to run the Spring Boot application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005", "-jar", "sofa-rpc-k8s-consumer-0.0.1-SNAPSHOT.jar"]
