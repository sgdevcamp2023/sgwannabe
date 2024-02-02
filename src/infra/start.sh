#!/bin/bash

cd "../backend/"

BACKEND_PWD=`pwd`

SERVER_ORDER=(
    "storage-server"
    "music-server"
    "streaming-server"
)

echo "$BACKEND_PWD"

for SERVER in ${SERVER_ORDER[@]}; do
    cd $BACKEND_PWD/$SERVER/
    docker-compose down
    docker-compose up -d --build
done
