FROM openjdk:17-jdk-slim

COPY target/*.jar lazada.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "lazada.jar"]