# Stage 1: Build the JAR and extract the version
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /proj
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the extracted version
#FROM openjdk:11-jre-slim

# Use the official OpenJDK base image with Java 11 on Alpine Linux
FROM openjdk:11-jre-slim
#FROM leegreiner/11-jre-alpine

WORKDIR /app

# copy JAR into image
COPY --from=build /proj/target/*.jar app.jar


RUN pwd
RUN ls -l

EXPOSE 8081

# run application with this command line
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]
CMD ["java", "-jar", "-Dspring.profiles.active=default", "-Dserver.port=8081", "app.jar"]
