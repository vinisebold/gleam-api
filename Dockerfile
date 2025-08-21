# ---- Estágio 1: Build ----
# Usamos uma imagem completa do Maven e JDK para compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de dependências do Docker
COPY pom.xml .
# Baixa as dependências. Se o pom.xml não mudar, esta camada não será executada novamente.
RUN mvn dependency:go-offline

# Agora copia o código-fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar (sempre limpa o target antes)
RUN mvn clean package -DskipTests


# ---- Estágio 2: Runtime ----
# Usamos uma imagem JRE (Java Runtime Environment) mínima baseada em Alpine Linux
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Cria um usuário e grupo não-root para rodar a aplicação (melhor prática de segurança)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Muda para o usuário não-root
USER appuser

# Define o nome do artefato com base no pom.xml.
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar

# Copia APENAS o .jar gerado do estágio de build para a imagem final
COPY --from=build /app/${JAR_FILE} app.jar

# Expõe a porta que a aplicação vai usar
EXPOSE 8080

# Define o comando para iniciar a aplicação com parâmetros de memória OTIMIZADOS
ENTRYPOINT ["java", "-Xmx256m", "-XX:MaxMetaspaceSize=96m", "-jar", "app.jar"]
