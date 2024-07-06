# Stage 1: Build the application
FROM maven:3.9.7-sapmachine-22 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]