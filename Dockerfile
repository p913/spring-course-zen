FROM maven:3.6.1-jdk-11 AS builder

ENV PROJECT_DIR=/opt/bookstock
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD bookstock.tgz .

RUN mvn install -DskipTests


FROM openjdk:11-jdk

ENV APP_VERSION=0.0.1-SNAPSHOT
ENV PROJECT_DIR=/opt/bookstock
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=builder $PROJECT_DIR/target/bookstock-mvc-classic-$APP_VERSION.jar $PROJECT_DIR/

EXPOSE 8080

CMD java -jar $PROJECT_DIR/bookstock-mvc-classic-$APP_VERSION.jar
