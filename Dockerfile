# ---- Estágio 1: Build ----
# Usamos uma imagem completa do Maven e JDK para compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

# Compila o projeto e gera o arquivo .jar
RUN mvn package -DskipTests


# ---- Estágio 2: Runtime ----
# Usamos uma imagem JRE (Java Runtime Environment) mínima baseada em Alpine Linux
# Esta imagem é muito menor e consome menos memória
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG JAR_FILE=/app/target/*.jar

# Copia APENAS o .jar gerado do estágio de build para a imagem final
COPY --from=build ${JAR_FILE} app.jar

# Expõe a porta que a aplicação vai usar
EXPOSE 8080

# Define o comando para iniciar a aplicação com parâmetros de memória OTIMIZADOS
# -Xmx256m: Define o heap máximo em 256MB.
# -XX:MaxMetaspaceSize=96m: Limita a memória para metadados de classes.
# "exec" permite que a aplicação receba sinais do Docker corretamente
ENTRYPOINT ["java", "-Xmx256m", "-XX:MaxMetaspaceSize=96m", "-jar", "app.jar"]
