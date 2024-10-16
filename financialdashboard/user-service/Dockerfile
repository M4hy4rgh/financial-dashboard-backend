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

#package stage
FROM openjdk:17-jdk-alpine

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/user-service.jar

#port
EXPOSE 8084

ENTRYPOINT ["java","-jar","/app/user-service.jar"]