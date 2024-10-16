# --------------
# Dockerfile for user-service
#---------------
# BUILD stage
#--

#base image
FROM gradle:8-jdk17-alpine AS builder

# Copy the application filess from the host machine to the image filesystem
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory within the image, for executing future commands
WORKDIR /home/gradle/src

COPY --chown=gradle:gradle gradle/ ./gradle
COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts ./

# Run gradle build within the container
RUN gradle --no-daemon build --stacktrace --parallel --info -x test || return 0

COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon


#package stage
FROM openjdk:17-jdk-alpine

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/user-service.jar

#port
EXPOSE 8084

ENTRYPOINT ["java","-jar","/app/user-service.jar"]