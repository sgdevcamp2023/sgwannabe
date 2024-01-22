from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk

class ElasticsearchConnector:
    def __init__(self, host, port):
        self.es = Elasticsearch([{'host': host, 'port': port, 'scheme': 'http'}])

    def index(self, index, data):
        # bulk(self.es, data, index=index)
        self.es.index(index=index, body=data)

    def search(self, index, query):
        result = self.es.search(index=index, body=query)
        return result