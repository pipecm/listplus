FROM eclipse-temurin:latest as build
ADD . /app
WORKDIR /app
RUN ./gradlew clean build

FROM eclipse-temurin:latest as service
COPY --from=build /app/target/user-0.0.1-SNAPSHOT.jar /app/listplus-api-user-0.0.1-SNAPSHOT.jar
RUN chmod +x /app/listplus-api-user-0.0.1-SNAPSHOT.jar && adduser listplus-api-user
USER listplus-api-user
EXPOSE 8081
ENTRYPOINT ["java", "-vimjar", "/app/listplus-api-user-0.0.1-SNAPSHOT.jar"]