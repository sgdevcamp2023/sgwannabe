#! /bin/bash

mongoimport -u "admin" -p "QOdmQodmsapomd41dM" --authenticationDatabase "admin" --host playlist-mongo --db playlist --collection playlist --type json --file /mongo-seed/00_dummy_playlist.json --jsonArray
