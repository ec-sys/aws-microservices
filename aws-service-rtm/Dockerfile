FROM openjdk:17-alpine

COPY target/service-rtm-0.1.jar app.jar

EXPOSE 8080 8085

CMD ["java", "-jar", "app.jar"]