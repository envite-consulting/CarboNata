FROM maven:3.9.7-eclipse-temurin-17 AS build-base
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk AS run-base

COPY --from=build-base target/fibonacci-worker-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]