# Use official OpenJDK image
FROM openjdk:17-slim

# Set working directory
WORKDIR /automation

# Install Maven with specific version for stability
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy only the necessary files
COPY pom.xml .
COPY src ./src

# Set environment variables from your config.properties
ENV GRID_ENABLED=true \
    GRID_URL=http://selenium-hub:4444/wd/hub \
    BROWSER_LIST=chrome,firefox,edge \
    HEADLESS=true \
    THREAD_COUNT=9 \
    PARALLEL_TESTS=true

# Command to run tests
CMD ["mvn", "clean", "test"]