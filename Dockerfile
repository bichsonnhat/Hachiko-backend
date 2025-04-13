# Use a compatible base image for Java 17
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file to the container
COPY target/java-spring-boot-mongodb-*.jar app.jar

# Expose the port your app runs on (optional)
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
