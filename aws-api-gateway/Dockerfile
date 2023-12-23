FROM openjdk:17-alpine

COPY target/api_gateway-0.1.jar app.jar

EXPOSE 8080 8085

CMD ["java", "-jar", "app.jar"]