# 1. Usa una máquina virtual pequeñita que ya tiene Java 21 instalado
FROM eclipse-temurin:21-jdk-alpine

# 2. Crea una carpeta llamada /app dentro de esa máquina virtual
WORKDIR /app

# 3. Copia las herramientas de Maven a la máquina virtual
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 4. Dale permisos para que Maven pueda ejecutarse
RUN chmod +x ./mvnw

# 5. Descarga las librerías (Spring Boot, H2, etc.)
RUN ./mvnw dependency:go-offline

# 6. Ahora sí, copia todo tu código Java a la máquina virtual
COPY src ./src

# 7. Dile a Maven que compile tu código y cree el archivo .jar
RUN ./mvnw clean package -DskipTests

# 8. Abre el puerto 8080 para que podamos conectarnos a la API
EXPOSE 8080

# 9. Esta es la orden final: cuando alguien encienda este contenedor, corre el .jar
CMD ["java", "-jar", "target/Champions-League-0.0.1-SNAPSHOT.jar"]
