from pydantic import BaseModel

from .album import Album
from .artist import Artist

class Music(BaseModel):
    id: str
    title: str
    playTime: str
    album: Album
    artist: Artist