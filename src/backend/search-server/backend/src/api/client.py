from fastapi import APIRouter, Query
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(prefix="/v1/api/search", tags=["client-search API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.get("/musics")
async def search_musics(
    music: str = Query(..., description="음원 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준 (latest 또는 popularity)"),
    start: int = Query(..., description="시작 위치")
):
    query = generate_generic_query("field", music, size, sort, start)

    results = es_connector.search(index="music", query=query)
    return {"results": results}

@router.get("/artists")
async def search_artists(
    artist: str = Query(..., description="아티스트 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    query = generate_generic_query("field", artist, size, sort, start)

    results = es_connector.search(index="artist", query=query)
    return {"results": results}

@router.get("/users")
async def search_users(
    user: str = Query(..., description="사용자명 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    query = generate_generic_query("field", user, size, sort, start)

    results = es_connector.search(index="user", query=query)
    return {"results": results}

@router.get("/playlists")
async def search_playlists(
    playlist: str = Query(..., description="플레이리스트 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    query = generate_generic_query("field", playlist, size, sort, start)

    results = es_connector.search(index="playlist", query=query)
    return {"results": results}

@router.get("/chatrooms")
async def search_chatrooms(
    chatroom: str = Query(..., description="채팅방 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    query = generate_generic_query("field", chatroom, size, sort, start)

    results = es_connector.search(index="chatroom", query=query)
    return {"results": results}

def generate_generic_query(field: str, query_value: str, size: int, sort: str, start: int):
    query = {
        "query": {
            "match": {field: query_value}
        },
        "size": size,
        "sort": [{"timestamp_field" if sort == "latest" else "popularity_field": {"order": "desc"}}],
        "from": start
    }
    return query
