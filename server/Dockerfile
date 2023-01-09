## DOCKERFILE INSPIRED BY: https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it
##  FROM user: @Mark O'Connor
#
## BUILD
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY features /home/app/features
copy pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package


## PACKAGE
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /home/app/target/quarkus-app /usr/src/quarkus-app
CMD java -Xmx64m -jar /usr/src/quarkus-app/quarkus-run.jar