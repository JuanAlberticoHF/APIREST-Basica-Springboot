FROM eclipse-temurin:21-jre-alpine

COPY ./target/apirest-basica-mongodb-0.6.0-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
