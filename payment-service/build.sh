#!/bin/bash
set -e
mvn clean package
docker stop rabbitMq_container
docker rm rabbitMq_container
docker-compose build payment-service

# This file is copied from the "Correlation Student Registration Example" zip file.
# Created by Hubert Baumeister. 
# Accessed on 2023-01-11