# Stage 1: Build the application
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

# Stage 2: Run SonarQube analysis
FROM maven:3.8.8-eclipse-temurin-21

# Set working directory
WORKDIR /app

# Copy the application code and built jar file from the builder stage
COPY --from=builder /app /app

# Set environment variables for SonarQube
ENV SONAR_PROJECT_KEY="yes-25-5-coupons"
ENV SONAR_PROJECT_NAME="yes-25-5-coupons"
ENV SONAR_HOST_URL="***"
ENV SONAR_TOKEN="***"

# Run SonarQube analysis
CMD ["mvn", "clean", "verify", "sonar:sonar", \
     "-Dspring.profile.active=ci", \
     "-Dsonar.projectKey=${SONAR_PROJECT_KEY}", \
     "-Dsonar.projectName=${SONAR_PROJECT_NAME}", \
     "-Dsonar.host.url=${SONAR_HOST_URL}", \
     "-Dsonar.login=${SONAR_TOKEN}"]
