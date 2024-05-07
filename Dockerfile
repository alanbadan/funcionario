FROM openjdk:11-jdk

WORKDIR /app

COPY target/curso-espanhol-0.0.1-SNAPSHOT.jar /app/curso-espanhol.jar

EXPOSE 8080

CMD [ "java", "-jar", "curso-espanhol.jar" ]
