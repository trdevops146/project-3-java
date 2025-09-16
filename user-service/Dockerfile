################################
FROM maven:3.6.9 as Builder
WORKDIR /app
COPY . .
RUN mvn clean package
################################
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --FROM=Builder /app/target/*.jar app-user-service.jar
CMD ["java", "-jar", "app-user-service.jar"]
