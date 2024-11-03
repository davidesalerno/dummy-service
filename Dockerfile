FROM maven:3.6.3-openjdk-17-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY ui /workspace/ui
RUN mvn -B -f pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java", "-XX:+UseSerialGC","-Xmx32m","-Xss256k","-XX:MaxRAM=64m","-jar","app.jar"]