# builder image
FROM openjdk:21-slim AS build

WORKDIR /usr/app/
COPY . .
RUN ./gradlew build --no-daemon

# runtime image
FROM openjdk:21-slim

WORKDIR /app

COPY --from=build usr/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
