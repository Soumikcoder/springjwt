FROM maven:3.9.9-eclipse-temurin-21
WORKDIR /app
RUN apt-get update && apt-get install -y git
COPY src/ src/
COPY pom.xml .
RUN mvn clean package -DskipTests
# Expose port
EXPOSE 9080
# Run jar (auto-pick jar file)
CMD ["java", "-jar", "/app/target/demo-0.0.1-SNAPSHOT.jar"]
