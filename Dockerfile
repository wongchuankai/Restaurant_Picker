# Stage 1: Build the app with Maven
FROM maven:3.9.12-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build fat JAR
RUN mvn clean package -DskipTests

# Stage 2: Create small runtime image
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the fat JAR from build stage
COPY --from=build /app/target/restaurant-picker-app-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
