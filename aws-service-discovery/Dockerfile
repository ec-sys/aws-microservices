FROM eclipse-temurin:17-jre-alpine

COPY target/service-discovery-0.1.jar app.jar

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]