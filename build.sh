#!/bin/bash
set -e

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd messaging-utilities-3.3
./build.sh
popd 

# Build the services
pushd student-id-service 
./build.sh
popd 

pushd student-registration-service
./build.sh
popd 
