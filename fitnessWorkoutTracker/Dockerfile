# Use Maven official image to build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Force annotation processing
RUN mvn clean compile

# Package the application
RUN mvn clean package -DskipTests

# Use a lightweight Java image for running the app
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/*.jar app.jar

# Expose port and run the application
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]