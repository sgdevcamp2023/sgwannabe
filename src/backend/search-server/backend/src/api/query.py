from fastapi import APIRouter, HTTPException
from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector

router = APIRouter(tags=["query API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

@router.get("/search")
def search(query: str, index: str):
    es_query = {"query": {"match": {"field_name": query}}}
    result = es_connector.search(index, es_query)
    
    if result.get("hits") and result["hits"].get("hits"):
        return {"data": result["hits"]["hits"]}
    else:
        raise HTTPException(status_code=404, detail="No matching results found.")
