# Message Queue Example

The project consists of 4 projects

- A Maven library for some utilities providing an abstraction to accessing the message queue in `libraries/messaging-utilities-3.2` which are installled using `mvn install` through the build script
- The student registration microservice in `student-registration-service` which calls the service in the student id microservice 
- The student id microservice in `student-id-service` 
- The end-to-end tests in `end_to_end_tests`

The main `docker-compose.yml` file is in the `end_to_end_tests`.

To know how the project is build, deployed, and tested, inspect `build_and_run.sh`.