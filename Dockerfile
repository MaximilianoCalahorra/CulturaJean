# Usa OpenJDK 22
FROM openjdk:22-jdk-slim

# Instala Maven
RUN apt-get update && apt-get install -y maven

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml
COPY pom.xml .

# Copia el resto del código
COPY src ./src

# Ejecuta Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Copia el .jar generado
COPY target/culturaJean-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
