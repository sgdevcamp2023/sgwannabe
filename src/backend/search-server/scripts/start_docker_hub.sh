#!/bin/sh

script_dir="$(cd "$(dirname "$0")" && pwd)"

cd "$script_dir"

cd ..

docker-compose -f docker-compose-hub.yml pull
docker-compose -f docker-compose-hub.yml down
docker-compose -f docker-compose-hub.yml up -d --build
