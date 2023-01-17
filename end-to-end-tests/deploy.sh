#!/bin/bash
set -e
docker image prune -f
docker-compose up -d rabbitMq
sleep 10
docker-compose up -d facade-service account-management-service payment-service token-management-service
