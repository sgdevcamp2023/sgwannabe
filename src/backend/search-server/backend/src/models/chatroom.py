from pydantic import BaseModel

from .playlist import Playlist, PlaylistNested

class Chatroom(BaseModel):
    id: str
    name: str
    playlist: Playlist

class ChatroomIndexRequest(BaseModel):
    id: str
    name: str
    playlist: PlaylistNested