FROM openjdk:21-alpine AS gradle-base
RUN microdnf install -y findutils && microdnf clean all
WORKDIR /project
# Copy gradle wrapper files and build configuration
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle gradle.properties ./
# Copy all source code
COPY ffmpeg-api /project
COPY app ./app
COPY logging-api ./logging-api
COPY db-api ./db-api
COPY app/command ./app/command
# Make gradlew executable
RUN chmod +x ./gradlew

# Build app service
FROM gradle-base AS app-build
RUN ./gradlew :app:clean :app:shadowJar

# Final app image
FROM openjdk:21-alpine AS app
WORKDIR /app
COPY --from=app-build /project/app/build/libs/*.jar app-service.jar
CMD ["java", "-jar", "app-service.jar"]

# Build command service
FROM gradle-base-alpine AS command-build
RUN ./gradlew :app:command:clean :app:command:shadowJar

# Final command image
FROM openjdk:21-alpine AS command
WORKDIR /app/command
COPY --from=command-build /project/app/command/build/libs/*.jar command-service.jar
CMD ["./gradlew", ":app:command:assemble"]

# Build logging service
FROM gradle-base AS logging-api-build
RUN ./gradlew :logging-api:clean :logging-api:shadowJar

# Final logging image
FROM openjdk:21-alpine AS logging-api
WORKDIR /logging-api
COPY --from=logging-api-build /project/logging-api/build/libs/*.jar logging-api-service.jar
CMD ["./gradlew", ":logging-api:assemble"]

# Build db-api service
FROM gradle-base AS db-api-build
RUN ./gradlew :db-api:clean :db-api:shadowJar

# Final db-api image
FROM openjdk:21-alpine AS db-api
WORKDIR /db-api
COPY --from=db-api-build /project/db-api/build/libs/*.jar db-api-service.jar
CMD ["./gradlew", ":db-api:assemble"]