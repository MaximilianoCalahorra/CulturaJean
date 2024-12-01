# Usa la imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Instala Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Configura el directorio de trabajo
WORKDIR /app

# Copia el código fuente al contenedor
COPY . .

# Ejecuta Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Usa OpenJDK 17 para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo .jar generado desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
