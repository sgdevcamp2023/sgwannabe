from pydantic import BaseModel
from typing import List
from .music import Music, MusicNested

class Playlist(BaseModel):
    id: str
    name: str
    musics: List[Music]
    thumbnail: str
    uid: str
    userName: str

class PlaylistIndexRequest(BaseModel):
    id: str
    name: str
    musics: List[MusicNested]
    thumbnail: str
    uid: str
    userName: str

class PlaylistNested(BaseModel):
    id: str
    name: str
    musics: List[MusicNested]
    thumbnail: str
    uid: str
    userName: str