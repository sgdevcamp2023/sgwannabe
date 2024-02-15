import py_eureka_client.eureka_client as eureka_client
from fastapi import FastAPI
from .api import client, indexing, query

# eureka_client 에서 nested async await 지원 위한 라이브러리
import nest_asyncio
nest_asyncio.apply()

# TODO: host env 으로 받기
eureka_client.init(
    eureka_server="http://localhost:34100",
    app_name="SEARCH-SERVER",
    instance_port=20000
)

app = FastAPI()

@app.get("/")
async def read_root():
    return {"Hello": "Search Server!"}

app.include_router(client.router)
app.include_router(query.router)
app.include_router(indexing.router)