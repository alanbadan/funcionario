FROM openjdk:11-jdk

WORKDIR /app

COPY target/espanhol-0.0.1-SNAPSHOT.jar /app/espanhol.jar

EXPOSE 8080

CMD [ "java", "-jar", "espanhol.jar" ]
