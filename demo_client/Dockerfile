## DOCKERFILE FOUND ON: https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
## From user @Mark O'Connor
#
# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY features /home/app/features
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/demo-client-SNAPSHOT-0.0.1.jar /home/app/demo.jar
ENTRYPOINT ["java","-jar","/home/app/demo.jar"]