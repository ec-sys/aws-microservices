FROM eclipse-temurin:17-jre-alpine

COPY target/service-post-0.1.jar app.jar

EXPOSE 80 443 8080

CMD ["java", "-jar", "app.jar"]