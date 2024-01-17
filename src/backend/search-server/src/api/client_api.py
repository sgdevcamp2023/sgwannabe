from fastapi import APIRouter, Query
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(tags=["client-search API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.get("/")
def read_root():
    return {"Hello": "Search Server!"}

@router.get("/search")
def unified_search(query: str = Query(..., description="통합 검색 쿼리")):
    """
    통합 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 통합 검색 결과
    """
    results = es_connector.unified_search(query)
    return {"results": results}

@router.get("/tracks")
def search_tracks(query: str = Query(..., description="음원 검색 쿼리")):
    """
    음원에 대한 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 음원 검색 결과
    """
    results = es_connector.search_tracks(query)
    return {"results": results}

@router.get("/artists")
def search_artists(query: str = Query(..., description="아티스트 검색 쿼리")):
    """
    아티스트에 대한 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 아티스트 검색 결과
    """
    results = es_connector.search_artists(query)
    return {"results": results}

@router.get("/members")
def search_artists(query: str = Query(..., description="사용자명 검색 쿼리")):
    """
    사용자명에 대한 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 아티스트 검색 결과
    """
    results = es_connector.search_members(query)
    return {"results": results}

@router.get("/playlists")
def search_artists(query: str = Query(..., description="플레이리스트 검색 쿼리")):
    """
    플레이리스트명에 대한 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 플레이리스트 검색 결과
    """
    results = es_connector.search_playlists(query)
    return {"results": results}

@router.get("/chatrooms")
def search_artists(query: str = Query(..., description="채팅방 검색 쿼리")):
    """
    채팅방명에 대한 검색 엔드포인트
    :param query: 검색 쿼리
    :return: Elasticsearch에서 반환된 채팅방 검색 결과
    """
    results = es_connector.search_chatrooms(query)
    return {"results": results}
