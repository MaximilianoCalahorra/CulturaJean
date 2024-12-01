# Usa la imagen base de OpenJDK 22
FROM openjdk:22-jdk-slim

# Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml (y otros archivos necesarios) al contenedor
COPY pom.xml ./

# Copia el wrapper de Maven (si lo usas) al contenedor
COPY mvnw .
COPY mvnw.cmd .

# Copia el archivo .jar generado al contenedor
COPY target/culturaJean-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
