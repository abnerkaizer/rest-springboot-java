FROM eclipse-temurin:21-jdk-alpine
COPY target/*.jar app.jar
LABEL authors="abner"

ENTRYPOINT ["java", "-jar", "/app.jar"]