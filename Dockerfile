FROM maven:3.8.2-jdk-11 AS build
LABEL authors="moetez ayari"
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /target/university-management-0.0.1-SNAPSHOT.jar /university-management-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/university-management-0.0.1.jar"]
