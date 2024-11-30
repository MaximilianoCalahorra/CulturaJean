# Usa la imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Configura el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml (y otros archivos necesarios) al contenedor
COPY pom.xml .

# Ejecuta la construcción del proyecto (esto generará el .jar)
RUN ./mvnw clean package

# Copia el archivo .jar generado al contenedor
COPY target/culturaJean0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]

