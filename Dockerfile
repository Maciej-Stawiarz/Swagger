# ---------- Building the app ----------
FROM maven:3.9.15-eclipse-temurin-25-alpine AS builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package spring-boot:repackage -DskipTests

# ---------- Building the image ----------
FROM eclipse-temurin:25-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]