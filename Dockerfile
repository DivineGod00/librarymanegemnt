FROM openjdk:17-jdk-alpine

# Set the working directory in the container
ARG JAR_FILE=target/*.jar

# Copy the compiled JAR file from the target directory to the container
COPY ./target/LibraryManagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 2207

# Command to run the application
ENTRYPOINT [ "java","-jar","/app.jar" ]