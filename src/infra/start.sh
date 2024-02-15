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

# 서버 시작 순서 조정, 의존성이 있는 경우 앞에 먼저 시작되어야 함

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
    docker-compose -f docker-compose-hub.yml up -d
done
