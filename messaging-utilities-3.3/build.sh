#!/bin/bash
set -e
mvn clean install
docker stop rabbitMq_container
docker rm rabbitMq_container
docker-compose up -d
sleep 5
