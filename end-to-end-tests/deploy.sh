#!/bin/bash
set -e
docker image prune -f
sleep 10
docker-compose up -d

