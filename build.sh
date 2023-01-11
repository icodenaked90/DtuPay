#!/bin/bash
set -e

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd RabbitMQService
./build.sh
popd 

# Build the services
pushd AccountManagementService 
./build.sh
popd 

#pushd student-registration-service
#./build.sh
#popd 
