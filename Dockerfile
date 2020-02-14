FROM maven:3.6.3-jdk-11 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:11-jre-slim
COPY --from=builder /app/target/backend-0.2.jar /
ARG db_username
ARG db_password
ENV TELLME_MONGODB_ATLAS_USERNAME=$db_username
ENV TELLME_MONGODB_ATLAS_PASSWORD=$db_password
EXPOSE 8080
CMD ["java", "-jar", "/backend-0.2.jar"]