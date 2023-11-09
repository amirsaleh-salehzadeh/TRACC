# Use an official Maven image as a build stage
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Download dependencies and package the application
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

# Use an official OpenJDK image as the base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the container
COPY --from=build /app/target/CCINETApplication.jar .

# Expose the port the app runs on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "CCINETApplication.jar"]
