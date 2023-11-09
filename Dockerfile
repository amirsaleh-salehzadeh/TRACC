# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Create a directory for the application in the container
RUN mkdir /app

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY target/CCINETApplication.jar /app/CCINETApplication.jar

# Expose the port the app runs on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "CCINETApplication.jar"]
