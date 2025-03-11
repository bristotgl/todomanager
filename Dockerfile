FROM eclipse-temurin:21
LABEL authors="bristotgl"
COPY target/todomanager-0.0.1-SNAPSHOT.jar todomanager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "todomanager-0.0.1-SNAPSHOT.jar"]