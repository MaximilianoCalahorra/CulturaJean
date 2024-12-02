# Etapa de construcción
FROM openjdk:22-jdk-slim AS builder
WORKDIR /app

# Instalar Maven manualmente
RUN apt-get update && apt-get install -y maven

# Copiar y construir el proyecto
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa final: Ejecutar la aplicación
FROM openjdk:22-jdk-slim
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/culturaJean-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto y definir el comando de inicio
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
