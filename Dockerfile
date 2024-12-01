# Usa una imagen de Maven para construir el proyecto
FROM maven:3.8.8-openjdk-22 AS build

# Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios para la construcción
COPY pom.xml ./
COPY src ./src

# Ejecuta el comando para construir el proyecto y generar el .jar
RUN mvn clean package -DskipTests

# Usa una imagen ligera de OpenJDK para el contenedor final
FROM openjdk:22-jdk-slim

# Configura el directorio de trabajo en el contenedor final
WORKDIR /app

# Copia el .jar generado desde el contenedor de construcción
COPY --from=build /app/target/culturaJean-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
