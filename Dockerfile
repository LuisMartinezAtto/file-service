FROM maven:3.8.6-amazoncorretto-17 AS build
WORKDIR /usr/src/dfsm-api
COPY . /usr/src/dfsm-api/
RUN mvn clean package
RUN cp /usr/src/dfsm-api/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Duser.timezone=GMT-6  ./app.jar"]
