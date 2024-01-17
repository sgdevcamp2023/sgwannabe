FROM python:3.9

WORKDIR /app

COPY . /app

RUN pip install --upgrade pip
RUN pip install -r requirements.txt

CMD ["uvicorn", "src.main:app", "--host", "0.0.0.0", "--port", "8000"]
