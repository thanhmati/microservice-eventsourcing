# Stage 1: Use Maven for building
FROM maven:3.8.5-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY ./discoverserver ./discoverserver
WORKDIR /app/discoverserver
RUN mvn clean package -DskipTests

# Staage 2: use OpenJDK for running
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","discoverserver.jar"]