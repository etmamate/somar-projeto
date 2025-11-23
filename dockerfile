# Dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar arquivos do Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Dar permissão ao mvnw e instalar dependências
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copiar código fonte
COPY src ./src

# Construir a aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "target/somar-back-0.0.1-SNAPSHOT.jar"]