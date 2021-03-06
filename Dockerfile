FROM openjdk:11
ADD target/simple-library-api.jar simple-library-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "simple-library-api.jar"]