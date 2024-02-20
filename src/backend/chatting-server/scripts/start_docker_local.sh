#!/bin/sh

script_dir="$(cd "$(dirname "$0")" && pwd)"

cd "$script_dir"

cd ..

docker-compose -f docker-compose-local.yml pull
docker-compose -f docker-compose-local.yml down
docker-compose -f docker-compose-local.yml up -d
