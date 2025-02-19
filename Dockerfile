# Usa una imagen base de Java 21
FROM eclipse-temurin:21-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR empaquetado de Spring Boot al contenedor
COPY target/curso-sb.jar /app/app.jar

# Expone el puerto en el que se ejecuta tu aplicación Spring Boot
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]



#Ejecutar los comandos
#docker build -t ms-spring-boot .
#docker run -p 8081:8081 ms-spring-boot