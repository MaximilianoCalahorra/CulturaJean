FROM openjdk:22-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml para resolver dependencias de Maven
COPY pom.xml .

# Copia el directorio src al contenedor
COPY src ./src

# Instala Maven si es necesario (para el entorno Docker)
RUN apt-get update && apt-get install -y maven

# Ejecuta Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Verifica si el archivo .jar se generó correctamente
RUN ls -l target/

# Copia el archivo .jar generado en la ruta correcta dentro del contenedor
COPY target/culturaJean-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
