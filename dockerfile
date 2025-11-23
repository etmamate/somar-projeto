FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Verificar se o JAR foi criado
RUN echo "=== Conteúdo da pasta target ==="
RUN ls -la target/
RUN echo "=== Nome do JAR ==="
RUN find target/ -name "*.jar"

EXPOSE 8080

CMD ["java", "-jar", "target/somar-back-0.0.1-SNAPSHOT.jar"]