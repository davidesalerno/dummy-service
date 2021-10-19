FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY ui /workspace/ui
RUN mvn -B -f pom.xml clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java", "-XX:+UseSerialGC","-Xmx32m","-Xss256k","-XX:MaxRAM=64m","-jar","app.jar"]