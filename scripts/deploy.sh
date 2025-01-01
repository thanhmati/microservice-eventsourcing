#!/usr/bin/env bash

# Sử dụng biến DOCKER_USERNAME
echo "Deploying with Docker username: $DOCKER_USERNAME"

BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "Deploying with branch: $BRANCH"


cd app/microservice-eventsourcing
git checkout $BRANCH
git pull

cat docker-compose.yml

docker compose -f docker-compose.yml down
docker compose -f docker-compose.yml pull
docker compose -f docker-compose.yml up -d
docker system prune -af