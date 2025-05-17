FROM maven:3.9.9-eclipse-temurin-21-alpine AS dev
WORKDIR /app
ENTRYPOINT [ "mvn", "clean", "spring-boot:run" ]

FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim AS prod
# RUN addgroup --system spring && adduser --system --group spring spring
# USER spring:spring
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
