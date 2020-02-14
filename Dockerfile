FROM maven:3.6.3-jdk-11 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=builder /app/target/backend-0.2.jar /
CMD ["java", "-jar", "/backend-0.2.jar"]