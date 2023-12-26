FROM openjdk:17
ADD target/sb-app.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]