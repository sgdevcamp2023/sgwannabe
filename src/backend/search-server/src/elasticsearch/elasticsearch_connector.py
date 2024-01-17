from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk

class ElasticsearchConnector:
    def __init__(self, host, port):
        self.es = Elasticsearch([{'host': host, 'port': port, 'scheme': 'http'}])

    def index_data(self, index, data):
        bulk(self.es, data, index=index)

    def search_data(self, index, query):
        result = self.es.search(index=index, body=query)
        return result