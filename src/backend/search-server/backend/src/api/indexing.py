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

@router.post("/index/music")
async def index_data(body: MusicIndexRequest = Body(embed=True)):
    """
    Music 데이터를 Elasticsearch에 색인합니다.
    """
    print(body)

    try:
        es_connector.index(index=f"music", data=body.model_dump())
        
        return {"message": f"music 색인 성공"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"music 색인 실패: {str(e)}")
    
@router.post("/index/artist")
async def index_data(body: ArtistIndexRequest = Body(embed=True)):
    """
    Artist 데이터를 Elasticsearch에 색인합니다.
    """
    print(body)

    try:
        es_connector.index(index=f"artist", data=body.model_dump())
        
        return {"message": f"artist 색인 성공"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"artist 색인 실패: {str(e)}")
    
@router.post("/index/album")
async def index_data(body: AlbumIndexRequest = Body(embed=True)):
    """
    Album 데이터를 Elasticsearch에 색인합니다.
    """
    print(body)

    try:
        es_connector.index(index=f"album", data=body.model_dump())
        
        return {"message": f"album 색인 성공"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"album 색인 실패: {str(e)}")
