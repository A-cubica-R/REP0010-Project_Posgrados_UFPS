# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Copiar todo el código fuente
COPY backend backend
COPY platform platform

# Compilar y empaquetar
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR generado desde el stage anterior
COPY --from=builder /app/platform/application/target/application-*.jar app.jar

# Exponer puerto (cambiar si tu app usa otro puerto)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
