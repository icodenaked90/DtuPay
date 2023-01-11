#!/bin/bash
set -e

# This file is copied from the "Correlation Student Registration Example" zip file.
# Created by Hubert Baumeister. 
# Accessed on 2023-01-11

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd messaging-utilities-3.3
./build.sh
popd 

# Build the services
pushd AccountManagementService 
./build.sh
popd 

#pushd student-registration-service
#./build.sh
#popd 


