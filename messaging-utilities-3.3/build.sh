#!/bin/bash
set -e
mvn clean install
docker-compose up -d
sleep 5
