#### order service dockerfile #
#### Stage 1 ##################
FROM maven:3.6.9 as Builder
WORKDIR /app
COPY . .
RUN mvn clean package
### Stage 2 ###################
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=Builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
################################