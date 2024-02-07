#!/bin/bash

docker network create --driver bridge lalala-network

script_dir="$(cd "$(dirname "$0")" && pwd)"

# Infra Setup

cd "$script_dir"

docker-compose down
docker-compose up -d --build

# Backend Setup

cd "../backend/"

BACKEND_PWD=`pwd`

SERVER_ORDER=(
    "storage-server"
    "music-server"
    "streaming-server"
    "music-uploader-server"
)

echo "$BACKEND_PWD"

for SERVER in ${SERVER_ORDER[@]}; do
    cd $BACKEND_PWD/$SERVER/
    docker-compose down
    docker-compose up -d --build
done
