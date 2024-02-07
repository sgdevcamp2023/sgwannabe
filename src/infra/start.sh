#!/bin/bash

docker network create --driver bridge lalala-network

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
