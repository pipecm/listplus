FROM eclipse-temurin:latest as build
ADD . /app
WORKDIR /app
RUN ./gradlew clean build

FROM eclipse-temurin:latest as service
COPY --from=build /app/build/libs/gateway-0.0.1-SNAPSHOT.jar /app/listplus-api-gateway-0.0.1-SNAPSHOT.jar
RUN chmod +x /app/listplus-api-gateway-0.0.1-SNAPSHOT.jar && adduser listplus-api-gateway
USER listplus-api-gateway
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/listplus-api-gateway-0.0.1-SNAPSHOT.jar"]
