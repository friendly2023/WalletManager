FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/wallet_manager-0.0.1-SNAPSHOT.jar /app/wallet-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/wallet-app.jar"]
