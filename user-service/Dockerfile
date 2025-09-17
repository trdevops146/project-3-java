################################
FROM maven:3.6 as builder
WORKDIR /app
COPY . .
RUN mvn clean package
################################
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app-user-service.jar
CMD ["java", "-jar", "app-user-service.jar"]
