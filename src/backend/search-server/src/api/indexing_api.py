from fastapi import APIRouter, HTTPException
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector
from pydantic import BaseModel

from ..models.music import Music
from ..models.album import Album
from ..models.artist import Artist

router = APIRouter(tags=["indexing API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

class IndexRequest(BaseModel):
    data: dict

@router.post("/index/{index_type}")
def index_data(index_type:str, request_body:IndexRequest):
    """
    데이터를 Elasticsearch에 색인합니다.
    """
    data = request_body.data

    try:
        es_connector.index(index=f"{index_type}_index", body=data)
        return {"message": f"{index_type} 색인 성공"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"{index_type} 색인 실패: {str(e)}")