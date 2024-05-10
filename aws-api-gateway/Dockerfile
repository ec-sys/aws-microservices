FROM eclipse-temurin:17.0.11_9-jre

COPY target/api_gateway-0.1.jar app.jar

EXPOSE 80 443 8080

CMD ["java", "-jar", "app.jar"]