FROM eclipse-temurin:21

WORKDIR /app

COPY target/naves-espaciales.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]