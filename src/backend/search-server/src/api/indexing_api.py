from fastapi import APIRouter
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(tags=["indexing API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.post("/index")
def index_data(index: str, data: dict):
    es_connector.index_data(index, [data])
    return {"message": "es 인덱싱 성공"}
