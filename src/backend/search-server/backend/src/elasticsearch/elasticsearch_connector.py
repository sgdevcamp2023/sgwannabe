from elasticsearch import Elasticsearch

class ElasticsearchConnector:
    def __init__(self, host, port):
        self.es = Elasticsearch([{'host': host, 'port': port, 'scheme': 'http'}])

    def index(self, index, data):
        self.es.index(index=index, body=data)

    def search(self, index, query):
        """
        Elasticsearch Query DSL을 사용한 검색 API
        
        :param index: 검색할 Elasticsearch 인덱스
        :param query: Elasticsearch Query DSL
        :return: 검색 결과
        """

        result = self.es.search(index=index, body=query)
        return result
