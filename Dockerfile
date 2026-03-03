FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","target/journalApp-0.0.1-SNAPSHOT.jar"]