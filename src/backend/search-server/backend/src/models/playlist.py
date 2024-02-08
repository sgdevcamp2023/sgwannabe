from pydantic import BaseModel

from .music import Music

class Playlist(BaseModel):
    id: str
    name: str
    musics: Music
    thumbnail: str
    uid: str
    userName: str