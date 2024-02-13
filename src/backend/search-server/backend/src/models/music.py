from pydantic import BaseModel

from .album import AlbumNested, Album
from .artist import Artist

class Music(BaseModel):
    id: str
    title: str
    playTime: str
    album: Album
    artist: Artist

class MusicIndexRequest(BaseModel):
    id: str
    title: str
    playTime: str
    album: AlbumNested
    artist: Artist

class MusicNested(BaseModel):
    id: str
    title: str
    playTime: str
    album: AlbumNested
    artist: Artist
