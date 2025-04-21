FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
COPY ./build/libs/auction-service-0.0.1-SNAPSHOT.jar auction-service.jar
ENTRYPOINT ["java", "-jar", "auction-service.jar"]