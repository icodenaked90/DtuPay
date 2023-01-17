#!/bin/bash
set -e
# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd messaging-utilities-3.3
./build.sh
popd 

pushd payment-service
./build.sh
popd

# Build the services
pushd AccountManagementService
./build.sh
popd 

pushd Facade
./build.sh
popd 
