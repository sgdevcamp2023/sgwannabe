from pydantic import BaseModel
from datetime import datetime

class Track(BaseModel):
    title: str
    album: str
    artist: str
    composer: str
    lyricist: str
    lyrics: str
    releaseDate: datetime
    duration: int