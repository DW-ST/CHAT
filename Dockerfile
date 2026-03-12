# ===== FASE 1: COMPILAR =====
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# ===== FASE 2: EJECUTAR =====
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/mesa-soporte-1.0.0.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]