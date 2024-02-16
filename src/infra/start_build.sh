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
    "discovery-server"

    "user-server"

    "auth-server"

    "storage-server"
    
    "music-server"
    "music-uploader-server"
    
    "streaming-server"
    
    "alarm-server"

    "chart-server"
    
    "chatting-server"
    
    "feed-server"

    "playlist-server"

    "search-server"

    "gateway-server"
)

for SERVER in ${SERVER_ORDER[@]}; do
    cd $BACKEND_PWD/$SERVER/
    docker-compose down
    docker-compose up -d --build
done
