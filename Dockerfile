FROM gradle:6.7.1-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew build --no-daemon

FROM openjdk:11-jre-slim

RUN mkdir -p /gitlab-mr-stat

WORKDIR /gitlab-mr-stat

COPY --from=build /home/gradle/src/build/libs/gitlab-mr-stat-*.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar "/gitlab-mr-stat/app.jar"
