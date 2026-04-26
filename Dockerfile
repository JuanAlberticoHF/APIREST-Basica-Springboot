FROM eclipse-temurin:21-jre-alpine

COPY ./target/apirest-basica-mysql2-0.7.0-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
