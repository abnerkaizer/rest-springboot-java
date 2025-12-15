FROM gcr.io/distroless/java21-debian13

COPY target/*.jar /app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app.jar"]
