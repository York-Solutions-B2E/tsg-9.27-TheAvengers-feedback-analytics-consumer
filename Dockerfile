# ---------------------- BUILD
# TODO maybe change build setup
FROM eclipse-temurin:24-jdk AS build

# Work inside /app
WORKDIR /app

# Copy Maven wrapper + POM first (allows dependency caching)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

# Pre-download dependencies (caches layers)
RUN ./mvnw -q dependency:go-offline

# Copy source AFTER dependencies are pulled
COPY src src

# Build the Spring Boot JAR
RUN ./mvnw -q clean package -DskipTests


# ---------------------- RUNTIME
FROM eclipse-temurin:24-jre AS runtime

WORKDIR /app

# Bring over the built JAR
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
