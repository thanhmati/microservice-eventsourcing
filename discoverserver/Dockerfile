# Use Maven for building
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./discoverserver ./discoverserver  
WORKDIR /app/discoverserver
RUN mvn clean package -DskipTests

# Use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/discoverserver/target/*.jar discoverserver.jar  
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discoverserver.jar"]
