FROM eclipse-temurin:21-jre-alpine

COPY ./target/apirest-basica-mongodb-0.5.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
