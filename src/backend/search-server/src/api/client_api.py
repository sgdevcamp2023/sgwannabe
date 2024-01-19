from fastapi import APIRouter, Query
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(prefix="/v1/api/search", tags=["client-search API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.get("/musics")
async def search_tracks(
    music: str = Query(..., description="음원 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    """
    음원에 대한 검색 엔드포인트입니다.
    :param music: 음원 검색 쿼리
    :param size: 가져올 결과의 크기
    :param sort: 정렬 기준
    :param start: 시작 위치
    :return: Elasticsearch에서 반환된 음원 검색 결과
    """
    results = es_connector.search_music(music, size=size, sort=sort, start=start)
    return {"results": results}

@router.get("/artists")
async def search_artists(
    artist: str = Query(..., description="아티스트 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    """
    아티스트에 대한 검색 엔드포인트입니다.
    :param artist: 아티스트 검색 쿼리
    :param size: 가져올 결과의 크기
    :param sort: 정렬 기준
    :param start: 시작 위치
    :return: Elasticsearch에서 반환된 아티스트 검색 결과
    """
    results = es_connector.search_artists(artist, size=size, sort=sort, start=start)
    return {"results": results}

@router.get("/users")
async def search_users(
    user: str = Query(..., description="사용자명 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    """
    사용자명에 대한 검색 엔드포인트입니다.
    :param user: 사용자 검색 쿼리
    :param size: 가져올 결과의 크기
    :param sort: 정렬 기준
    :param start: 시작 위치
    :return: Elasticsearch에서 반환된 사용자 검색 결과
    """
    results = es_connector.search_users(user, size=size, sort=sort, start=start)
    return {"results": results}

@router.get("/playlists")
async def search_playlists(
    playlist: str = Query(..., description="플레이리스트 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    """
    플레이리스트명에 대한 검색 엔드포인트입니다.
    :param playlist: 플레이리스트 검색 쿼리
    :param size: 가져올 결과의 크기
    :param sort: 정렬 기준
    :param start: 시작 위치
    :return: Elasticsearch에서 반환된 플레이리스트 검색 결과
    """
    results = es_connector.search_playlists(playlist, size=size, sort=sort, start=start)
    return {"results": results}

@router.get("/chatrooms")
async def search_chatrooms(
    chatroom: str = Query(..., description="채팅방 검색 쿼리"),
    size: int = Query(..., description="가져올 결과의 크기"),
    sort: str = Query(..., description="정렬 기준"),
    start: int = Query(..., description="시작 위치")
):
    """
    채팅방명에 대한 검색 엔드포인트입니다.
    :param chatroom: 채팅방 검색 쿼리
    :param size: 가져올 결과의 크기
    :param sort: 정렬 기준
    :param start: 시작 위치
    :return: Elasticsearch에서 반환된 채팅방 검색 결과
    """
    results = es_connector.search_chatrooms(chatroom, size=size, sort=sort, start=start)
    return {"results": results}
