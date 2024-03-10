FROM maven:3.9.6-ibm-semeru-21-jammy AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests


FROM openjdk:21-jdk-slim-bullseye
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
