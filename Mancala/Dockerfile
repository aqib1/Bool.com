FROM adoptopenjdk/openjdk15
ADD controller/target/controller-1.0-SNAPSHOT.jar controller-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "controller-1.0-SNAPSHOT.jar"]
EXPOSE 8080