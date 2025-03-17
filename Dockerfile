FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app
COPY .mvn/ .mvn
COPY .mvn/wrapper/ .mvn/wrapper/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw && ./mvnw dependency:resolve

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache libstdc++
RUN busybox mkdir -p /data

COPY --from=build /app/target/sc-verify-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]