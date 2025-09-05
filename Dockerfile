# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml et télécharger les dépendances en cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source et compiler
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jdk-jammy AS runtime

WORKDIR /app

# Copier le jar depuis l'étape build
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port (défini aussi dans docker-compose)
EXPOSE 8080

# Healthcheck Docker
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD curl -f http://localhost:8080/api/ping || exit 1

ENTRYPOINT ["java","-jar","app.jar"]