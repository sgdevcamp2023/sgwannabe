#!/bin/bash

# develop 작업 내용을 모든 서버 develop 에 rebase 으로 합칩니다.

git checkout develop
git pull
git pull origin develop

SERVER_ORDER=(
    "discovery/develop"

    "user/develop"

    "auth/develop"

    "storage/develop"
    
    "music/develop"
    "music-uploader/develop"
    
    "streaming/develop"
    
    "alarm/develop"

    "chart/develop"
    
    "chatting/develop"
    
    "feed/develop"

    "playlist/develop"

    "search/develop"

    "gateway/develop"
)

for SERVER in ${SERVER_ORDER[@]}; do
    git checkout $SERVER
    git pull
    git pull origin $SERVER
    git rebase develop
    git push -f
done

git checkout develop
