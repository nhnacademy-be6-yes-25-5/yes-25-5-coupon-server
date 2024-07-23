FROM jenkins/jenkins:lts

# Install necessary packages
USER root
RUN apt-get update && apt-get install -y \
    tzdata \
    openjdk-21-jdk \
    maven && \
    ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME for JDK 21
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Create the working directory
RUN mkdir -p /app

# Set the working directory
WORKDIR /app

# Install Jenkins plugins
RUN jenkins-plugin-cli --plugins \
    git \
    docker-workflow \
    ssh-agent \
    publish-over-ssh \
    junit \
    workflow-aggregator \
    sonar \
    slack

# Copy JCasC configuration file
COPY jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml

# Set environment variable for JCasC
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/jenkins.yaml

# Switch back to the jenkins user
USER jenkins

# Copy the pom.xml and download dependencies
COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY . /app

# Build the application
RUN mvn package

# Default command
CMD ["java", "-jar", "target/couponApi-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

# Expose the required ports
EXPOSE 8080
EXPOSE 50000
