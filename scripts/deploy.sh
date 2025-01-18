BRANCH=$(git rev-parse --abbrev-ref HEAD)

echo "Deploying with branch: $BRANCH"

cd app/microservice-eventsourcing/
git fetch -a
git checkout $BRANCH
git pull

docker compose -f docker-compose.yml down
docker compose -f docker-compose.yml pull discoveryserver
docker compose -f docker-compose.yml up -d discoveryserver
docker system prune -af