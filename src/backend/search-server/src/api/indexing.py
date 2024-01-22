from fastapi import APIRouter, HTTPException, Depends
from fastapi.params import Body
from typing import Type, Any

from ..elasticsearch.elasticsearch_connector import ElasticsearchConnector
from pydantic import BaseModel

from ..models.music import Music
from ..models.album import Album
from ..models.artist import Artist

import json

router = APIRouter(tags=["색인 API"])
es_connector = ElasticsearchConnector(host='elasticsearch', port=9200)

class MusicIndexRequest(BaseModel):
    id: str
    title: str
    playTime: str
    album: Album
    artist: Artist

class AlbumIndexRequest(BaseModel):
    id: str
    title: str
    type: str
    releasedAt: str
    artist: Artist

class ArtistIndexRequest(BaseModel):
    name: str
    gender: str
    type: str
    agency: str

index_type_models = {
    'album': AlbumIndexRequest,
    'artist': ArtistIndexRequest,
    'music': MusicIndexRequest,
}

def get_model(index_type: str) -> Type[BaseModel]:
    model = index_type_models.get(index_type.lower())
    if model is None:
        raise HTTPException(status_code=400, detail=f"잘못된 인덱스 유형: {index_type}")
    return model

@router.post("/index/{index_type}")
async def index_data(index_type: str, body: MusicIndexRequest = Body(embed=True)):
    """
    데이터를 Elasticsearch에 색인합니다.
    """
    # print(body.body.model_dump())
    print(body)

    try:
        # 인덱스 유형에 해당하는 Pydantic 모델 가져오기
        # model = get_model(index_type)

        # 데이터를 지정된 모델로 유효성 검사 및 변환
        # index_data = model.model_construct(**body.dict())

        # Elasticsearch 색인 수행
        es_connector.index(index=f"{index_type}_index", data=body.dict())
        
        return {"message": f"{index_type} 색인 성공"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"{index_type} 색인 실패: {str(e)}")
