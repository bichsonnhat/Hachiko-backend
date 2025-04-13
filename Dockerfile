# ========== Step 1: Build ==========
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy all source code
COPY . .

# Build the app and package the jar
RUN mvn clean package -DskipTests

# ========== Step 2: Run ==========
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from the build stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
