#!/bin/sh

script_dir="$(cd "$(dirname "$0")" && pwd)"

cd "$script_dir"

cd ..

docker-compose down
docker-compose up -d
