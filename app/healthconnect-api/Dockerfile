# ==========================
# Stage 1 - Build
# ==========================
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy only pom.xml first
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline

# Copy application source
COPY src ./src

# Build application
RUN mvn clean package -DskipTests


# ==========================
# Stage 2 - Runtime
# ==========================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /build/target/healthconnect-api-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]