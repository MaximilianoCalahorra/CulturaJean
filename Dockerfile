# Primera etapa: Construcción del proyecto
FROM maven:3.8.7-openjdk-22-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Segunda etapa: Imagen de producción
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/culturaJean-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
