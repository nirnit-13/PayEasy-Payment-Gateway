# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies (cached if pom.xml unchanged)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

# Install curl for health checks
RUN apk add --no-cache curl

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/payeasy-backend.jar app.jar

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port (Render uses PORT env variable)
EXPOSE ${PORT:-8080}

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dserver.port=${PORT}", \
  "-Dspring.profiles.active=prod", \
  "-jar", \
  "app.jar"]