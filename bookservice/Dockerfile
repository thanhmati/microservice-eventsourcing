# Use Maven for building
FROM maven:3.8.5-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy commonservice and bookservice
COPY ./commonservice ./commonservice
COPY ./bookservice ./bookservice

# Build commonservice first
WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

# Build bookservice
WORKDIR /app/bookservice
RUN mvn clean package -DskipTests

# Use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "bookservice.jar"]
