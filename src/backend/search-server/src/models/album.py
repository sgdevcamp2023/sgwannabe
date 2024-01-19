from pydantic import BaseModel
from datetime import datetime

from artist import Artist


class Album(BaseModel):
    id: str
    title: str
    type: str
    releasedAt: datetime
    artist: Artist