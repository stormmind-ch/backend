# Stage 1: Build the application using Gradle
FROM gradle:8.13-jdk23 AS builder

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy gradle wrapper scripts and wrapper config files
COPY --chown=gradle:gradle gradlew gradlew
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./

# Copy the source code and models directory
COPY --chown=gradle:gradle src/ src/
COPY --chown=gradle:gradle models/ models/

# Build the application
RUN ./gradlew clean build --no-daemon

# Stage 2: Create the runtime image
FROM openjdk:23

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Copying the .csv files
COPY --from=builder /home/gradle/project/src/main/resources/municipalities_coordinates_newest.csv ./municipalities_coordinates_newest.csv
COPY --from=builder /home/gradle/project/src/main/resources/municiaplities_to_cluster_centroid_6.csv ./municiaplities_to_cluster_centroid_6.csv
COPY --from=builder /home/gradle/project/src/main/resources/model_to_cluster_file.csv ./model_to_cluster_file.csv
# Copy the models directory from the builder stage
COPY --from=builder /home/gradle/project/models/ models/

# Expose the application's port
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
