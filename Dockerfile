FROM eclipse-temurin:17-jdk
ARG JAR_FILE=./build/libs/chanzipup-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar
COPY src/main/resources/application-prod.yml ./src/main/resources/
COPY src/main/resources/application-secrets-prod.yml ./src/main/resources/

ENTRYPOINT ["java", "-jar", "/app.jar"]
