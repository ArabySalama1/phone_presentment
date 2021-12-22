FROM maven:3.6-jdk-8 as maven
WORKDIR /jumia_app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src

RUN mvn package && cp target/phone_presentment-0.0.1.jar phone_presentment.jar

# Rely on Docker's multi-stage build to get a smaller image based on JRE
FROM openjdk:8-jre-alpine
LABEL maintainer="xxxxx@xxx.com"
WORKDIR /jumia_app
COPY --from=maven /jumia_app/phone_presentment.jar ./phone_presentment.jar

# VOLUME /tmp  # optional
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jumia_app/phone_presentment.jar"]