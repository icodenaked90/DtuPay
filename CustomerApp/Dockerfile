## DOCKERFILE INSPIRED BY: https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
##  FROM user: @Mark O'Connor
#
## BUILD
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY features /home/app/features
COPY pom.xml /home/app
# We skip tests since we cannot be sure that the backend is running during build time
# Tests are run in the container once the backend is running
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

## PACKAGE
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/demo-client-SNAPSHOT-0.0.1.jar /home/app/demo.jar
ENTRYPOINT ["java","-jar","/home/app/demo.jar"]