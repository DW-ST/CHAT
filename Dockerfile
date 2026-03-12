# ===== FASE 1: BUILD =====
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY backend ./backend

WORKDIR /build/backend

RUN mvn clean package -DskipTests

# ===== FASE 2: RUN =====
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /build/backend/target/mesa-soporte-1.0.0.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]