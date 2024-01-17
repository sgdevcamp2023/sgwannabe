from fastapi import APIRouter
from elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(tags=["indexing API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.post("/index")
def index_data(index: str, data: dict):
    es_connector.index_data(index, [data])
    return {"message": "es 인덱싱 성공"}

# # dummy
# dummy_data = [
#     {
#         "title": "Song 1",
#         "album": "Album 1",
#         "artist": "Artist 1",
#         "composer": "Composer 1",
#         "lyricist": "Lyricist 1",
#         "lyrics": "Lyrics 1",
#         "releaseDate": "2022-01-01T00:00:00",
#         "duration": 180
#     },
#     {
#         "title": "Song 2",
#         "album": "Album 2",
#         "artist": "Artist 2",
#         "composer": "Composer 2",
#         "lyricist": "Lyricist 2",
#         "lyrics": "Lyrics 2",
#         "releaseDate": "2022-02-01T00:00:00",
#         "duration": 200
#     },
#     {
#         "title": "Song 3",
#         "album": "Album 3",
#         "artist": "Artist 3",
#         "composer": "Composer 3",
#         "lyricist": "Lyricist 3",
#         "lyrics": "Lyrics 3",
#         "releaseDate": "2022-03-01T00:00:00",
#         "duration": 160
#     },
#     {
#         "title": "Song 4",
#         "album": "Album 4",
#         "artist": "Artist 4",
#         "composer": "Composer 4",
#         "lyricist": "Lyricist 4",
#         "lyrics": "Lyrics 4",
#         "releaseDate": "2022-04-01T00:00:00",
#         "duration": 220
#     },
#     {
#         "title": "Song 5",
#         "album": "Album 5",
#         "artist": "Artist 5",
#         "composer": "Composer 5",
#         "lyricist": "Lyricist 5",
#         "lyrics": "Lyrics 5",
#         "releaseDate": "2022-05-01T00:00:00",
#         "duration": 190
#     },
#     {
#         "title": "Song 6",
#         "album": "Album 6",
#         "artist": "Artist 6",
#         "composer": "Composer 6",
#         "lyricist": "Lyricist 6",
#         "lyrics": "Lyrics 6",
#         "releaseDate": "2022-06-01T00:00:00",
#         "duration": 180
#     },
#     {
#         "title": "Song 7",
#         "album": "Album 7",
#         "artist": "Artist 7",
#         "composer": "Composer 7",
#         "lyricist": "Lyricist 7",
#         "lyrics": "Lyrics 7",
#         "releaseDate": "2022-07-01T00:00:00",
#         "duration": 210
#     },
#     {
#         "title": "Song 8",
#         "album": "Album 8",
#         "artist": "Artist 8",
#         "composer": "Composer 8",
#         "lyricist": "Lyricist 8",
#         "lyrics": "Lyrics 8",
#         "releaseDate": "2022-08-01T00:00:00",
#         "duration": 200
#     },
#     {
#         "title": "Song 9",
#         "album": "Album 9",
#         "artist": "Artist 9",
#         "composer": "Composer 9",
#         "lyricist": "Lyricist 9",
#         "lyrics": "Lyrics 9",
#         "releaseDate": "2022-09-01T00:00:00",
#         "duration": 170
#     },
#     {
#         "title": "Song 10",
#         "album": "Album 10",
#         "artist": "Artist 10",
#         "composer": "Composer 10",
#         "lyricist": "Lyricist 10",
#         "lyrics": "Lyrics 10",
#         "releaseDate": "2022-10-01T00:00:00",
#         "duration": 150
#     }
# ]

# for idx, data in enumerate(dummy_data, 1):
#     es_connector.index_data(index="tracks", id=idx, body=data)