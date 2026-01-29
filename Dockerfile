#FROM maven:3.9.0-eclipse-temurin-17 AS build
#
#WORKDIR /app
#
#COPY pom.xml .
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:17-jdk-alpine
#
#RUN addgroup -S appgroup && adduser -S appuser -G appgroup
#USER appuser
#
#WORKDIR /app
#
#COPY --from=build /app/target/*.jar app.jar
#
#EXPOSE 8080
#
#HEALTHCHECK --interval=30s --timeout=5s --start-period=10s \
#  CMD curl -f http://localhost:8080/actuator/health || exit 1
#
#ENTRYPOINT ["java","-jar","/app/app.jar"]