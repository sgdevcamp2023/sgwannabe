from fastapi import FastAPI
from .api import client, indexing, query;

app = FastAPI()

@app.get("/")
async def read_root():
    return {"Hello": "Search Server!"}

app.include_router(client.router)
app.include_router(query.router)
app.include_router(indexing.router)