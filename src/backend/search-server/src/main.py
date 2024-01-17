from fastapi import FastAPI
from .api import client_api, query_api, indexing_api;
app = FastAPI()

app.include_router(client_api.router)
app.include_router(query_api.router)
app.include_router(indexing_api.router)