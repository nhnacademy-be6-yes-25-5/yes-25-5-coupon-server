# Use the official Maven image with Java 21
FROM maven:3.8.8-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY . .

# Set environment variables
ENV JWT_SECRET=this-is-coupon-jwt-secret-key-i-am-hungry

# Build the application with the environment variable
RUN mvn package -Djwt.secret=${JWT_SECRET}

# Use a smaller base image for the final image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/target/couponApi-0.0.1-SNAPSHOT.jar ./app.jar

# Default command to run the application
CMD ["java", "-jar", "app.jar"]
