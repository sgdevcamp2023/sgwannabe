FROM python:3.9

WORKDIR /app

COPY . /app

RUN pip install --upgrade pip
RUN pip install -r requirements.txt
RUN elasticsearch-plugin install analysis-nori

COPY elasticsearch.yml /usr/share/elasticsearch/config/

CMD ["uvicorn", "src.main:app", "--host", "0.0.0.0", "--port", "8000"]
