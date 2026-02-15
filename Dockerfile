# ----------- BUILD STAGE -----------
FROM maven:3.9.10-eclipse-temurin-21 AS build

WORKDIR /app

# Copy everything
COPY . .

# Build the jar
RUN mvn clean package -DskipTests


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/gateway-microservice-hms-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
