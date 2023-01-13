FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/facade-service-1.0.0-runner.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx64m -jar facade-service-1.0.0-runner.jar

# This file is copied from the "Correlation Student Registration Example" zip file.
# Created by Hubert Baumeister.
# Accessed on 2023-01-11